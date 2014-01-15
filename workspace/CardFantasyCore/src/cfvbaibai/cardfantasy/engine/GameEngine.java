package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.CardFantasyUserRuntimeException;
import cfvbaibai.cardfantasy.GameOverSignal;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;

public class GameEngine {

    private StageInfo stage;
    public StageInfo getStage() {
        return stage;
    }

    public GameEngine(GameUI ui, Rule rule) {
        this.stage = new StageInfo(new Board(), ui, rule);
    }
    
    public static GameResult play1v1(GameUI ui, Rule rule, PlayerInfo p1, PlayerInfo p2) {
        GameEngine engine = new GameEngine(ui, rule);
        engine.RegisterPlayers(p1, p2);
        return engine.playGame();
    }

    private Player getActivePlayer() {
        return this.stage.getActivePlayer();
    }

    private Player getInactivePlayer() {
        return this.stage.getInactivePlayers().get(0);
    }
    
    private void validateDeck(PlayerInfo playerInfo) {
        if (playerInfo.isNormalPlayer() && playerInfo.getLevel() > 150) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的等级过高：%d！玩家等级不得超过150级。",
                    playerInfo.getId(), playerInfo.getLevel()));
        }
        Collection <Card> cards = playerInfo.getCards();
        Collection <Rune> runes = playerInfo.getRunes();
        if (cards.size() > playerInfo.getCardSlot()) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的卡牌槽不足！%s 卡牌槽数：%d, 卡组卡牌数：%d",
                    playerInfo.getId(), playerInfo.getId(), playerInfo.getCardSlot(), cards.size()));
        }
        if (cards.size() == 0) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "没有为 %s 配置任何卡牌！", playerInfo.getId()));
        }
        if (runes.size() > playerInfo.getRuneSlot()) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的符文槽不足！%s 符文槽数：%d, 卡组符文数：%d",
                    playerInfo.getId(), playerInfo.getId(), playerInfo.getRuneSlot(), runes.size()));
        }
        int cost = 0;
        for (Card card : playerInfo.getCards()) {
            cost += card.getCost();
        }
        if (cost > playerInfo.getMaxCost()) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的COST不足！%s 的最大COST：%d, 卡组COST: %d",
                    playerInfo.getId(), playerInfo.getId(), playerInfo.getMaxCost(), cost));
        }
    }

    public void RegisterPlayers(PlayerInfo player1Info, PlayerInfo player2Info) {
        validateDeck(player1Info);
        validateDeck(player2Info);
        stage.addPlayer(player1Info);
        stage.addPlayer(player2Info);
    }

    public GameResult playGame() {
        this.stage.gameStarted();
        this.stage.setRound(1);
        GameResult result = proceedGame();
        this.stage.getUI().gameEnded(result);
        return result;
    }

    private GameResult proceedGame() {
        Phase phase = Phase.开始;
        Phase nextPhase = Phase.未知;
        try {
            while (true) {
                if (phase == Phase.开始) {
                    nextPhase = roundStart();
                } else if (phase == Phase.抽卡) {
                    nextPhase = drawCard();
                } else if (phase == Phase.准备) {
                    nextPhase = standby();
                } else if (phase == Phase.召唤) {
                    nextPhase = summonCards();
                } else if (phase == Phase.战斗) {
                    nextPhase = battle();
                } else if (phase == Phase.结束) {
                    nextPhase = roundEnd();
                } else {
                    throw new CardFantasyRuntimeException(String.format("Unknown phase encountered: %s", phase));
                }
                stage.getUI().phaseChanged(getActivePlayer(), phase, nextPhase);
                phase = nextPhase;
                nextPhase = Phase.未知;
            }
        } catch (GameOverSignal signal) {
            return stage.result(this.stage.getPlayers().get(0), GameEndCause.战斗超时);
        } catch (HeroDieSignal signal) {
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.英雄死亡);
        } catch (AllCardsDieSignal signal) {
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.卡片全灭);
        }
    }

    private Player getOpponent(Player player) {
        return this.getActivePlayer() == player ? this.getInactivePlayer() : this.getActivePlayer();
    }

    private Phase summonCards() throws HeroDieSignal {
        Player player = this.getActivePlayer();
        List<CardInfo> summonedCards = new ArrayList<CardInfo>();
        for (CardInfo card : player.getHand().toList()) {
            if (card.getSummonDelay() == 0) {
                summonedCards.add(card);
            }
        }

        for (CardInfo summonedCard : summonedCards) {
            player.getHand().removeCard(summonedCard);
            this.stage.getResolver().summonCard(player, summonedCard, null);
        }

        player.getField().compact();
        this.getInactivePlayer().getField().compact();
        
        for (CardInfo card : this.getActivePlayer().getField().getAliveCards()) {
            removeStatus(card, CardStatusType.虚弱);
        }

        return Phase.准备;
    }

    private Phase standby() throws HeroDieSignal {
        this.stage.getResolver().activateRunes(this.getActivePlayer(), this.getInactivePlayer());
        this.stage.getResolver().resolvePreAttackRune(this.getActivePlayer(), this.getInactivePlayer());
        return Phase.战斗;
    }

    private Phase roundEnd() {
        Collection<CardInfo> allHandCards = this.stage.getAllHandCards();
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay > 0) {
                card.setSummonDelay(summonDelay - 1);
            }
        }

        Player previousPlayer = getActivePlayer();
        this.stage.setRound(stage.getRound() + 1);
        this.stage.getUI().roundEnded(previousPlayer, stage.getRound());
        int nextPlayerNumber = (this.stage.getActivePlayerNumber() + 1) % stage.getPlayerCount();
        this.stage.setActivePlayerNumber(nextPlayerNumber);
        Player nextPlayer = this.getActivePlayer();
        stage.getUI().playerChanged(previousPlayer, nextPlayer);
        return Phase.开始;
    }

    private Phase battle() throws HeroDieSignal {
        /***
         * Algorithm: For each card in field of active user: Check whether
         * target player has a card in field in the same position - Yes: Attack.
         * Card died? - Yes: Move card to grave. Leave an empty position. - No:
         * Go on. - No: Attack Hero. Trigger hero HP check. Remove all empty
         * position in fields.
         */

        FeatureResolver resolver = stage.getResolver();
        GameUI ui = stage.getUI();

        ui.battleBegins();
        Field myField = getActivePlayer().getField();
        Field opField = getInactivePlayer().getField();

        this.stage.getUI().compactField(myField);
        myField.compact();
        this.stage.getUI().compactField(opField);
        opField.compact();

        for (int i = 0; i < myField.size(); ++i) {
            if (myField.getCard(i) == null) {
                continue;
            }

            CardInfo card = myField.getCard(i);
            ui.cardActionBegins(card);
            CardStatus status = myField.getCard(i).getStatus();
            boolean underControl = false;
            if (status.containsStatus(CardStatusType.迷惑)) {
                underControl = true;
                ui.confused(myField.getCard(i));
                resolver.resolvePreAttackHeroFeature(myField.getCard(i), getActivePlayer());
                resolver.attackHero(myField.getCard(i), getActivePlayer(), null, myField.getCard(i).getCurrentAT());
                removeStatus(myField.getCard(i), CardStatusType.迷惑);
                removeStatus(myField.getCard(i), CardStatusType.冰冻);
                removeStatus(myField.getCard(i), CardStatusType.锁定);
                removeStatus(myField.getCard(i), CardStatusType.麻痹);
            }
            else if (
                status.containsStatus(CardStatusType.冰冻) ||
                status.containsStatus(CardStatusType.锁定) ||
                status.containsStatus(CardStatusType.虚弱)) {
                underControl = true;
                ui.cannotAction(myField.getCard(i));
                removeStatus(myField.getCard(i), CardStatusType.冰冻);
                removeStatus(myField.getCard(i), CardStatusType.锁定);
            } else {
                tryAttackEnemy(myField, opField, i);
            }

            resolver.resolveDebuff(myField.getCard(i), CardStatusType.中毒);
            resolver.resolveDebuff(myField.getCard(i), CardStatusType.燃烧);

            if (!underControl) {
                // 回春
                resolver.resolveCardRoundEndingFeature(myField.getCard(i));
            }
            
            // 解除状态
            removeStatus(myField.getCard(i), CardStatusType.中毒);
            ui.cardActionEnds(card);
        }

        this.stage.getUI().compactField(myField);
        myField.compact();
        this.stage.getUI().compactField(opField);
        opField.compact();

        return Phase.结束;
    }
    
    private void removeStatus(CardInfo card, CardStatusType statusType) {
        if (card == null) {
            return;
        }
        if (card.removeStatus(statusType)) {
            this.stage.getUI().removeCardStatus(card, statusType);
        }
    }

    private void tryAttackEnemy(Field myField, Field opField, int i) throws HeroDieSignal {
        FeatureResolver resolver = this.stage.getResolver();
        resolver.resolvePreAttackCardFeature(myField.getCard(i), opField.getCard(i), true);
        resolver.resolvePreAttackFeature(myField.getCard(i), getInactivePlayer());
        if (myField.getCard(i) == null) {
            return;
        }
        if (opField.getCard(i) == null) {
            resolver.resolvePreAttackHeroFeature(myField.getCard(i), getInactivePlayer());
            resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getCurrentAT());
        } else {
            tryAttackCard(myField, opField, i);
        }
        
        // Remove lasting effects
        resolver.removeTempEffects(myField.getCard(i));
        //
        resolver.resolvePostAttackFeature(myField.getCard(i), getInactivePlayer());
    }

    private void tryAttackCard(Field myField, Field opField, int i) throws HeroDieSignal {
        FeatureResolver resolver = this.stage.getResolver();
        resolver.resolvePreAttackCardFeature(myField.getCard(i), opField.getCard(i), false);
        if (opField.getCard(i) == null) {
            resolver.resolvePreAttackHeroFeature(myField.getCard(i), getInactivePlayer());
            resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getCurrentAT());
        } else {
            processAttackCard(myField, opField, i);
        }
    }

    private void processAttackCard(Field myField, Field opField, int i) throws HeroDieSignal {
        CardInfo defender = opField.getCard(i);
        FeatureResolver resolver = this.stage.getResolver();
        GameUI ui = this.stage.getUI();
        if (myField.getCard(i).getStatus().containsStatus(CardStatusType.麻痹)) {
            removeStatus(myField.getCard(i), CardStatusType.麻痹);
            return;
        }
        for (FeatureInfo featureInfo : myField.getCard(i).getNormalUsableFeatures()) {
            if (featureInfo.getFeature().getType() == FeatureType.横扫) {
                ui.useSkill(myField.getCard(i), defender, featureInfo.getFeature(), true);
            }
        }
        OnDamagedResult damagedResult = resolver.attackCard(myField.getCard(i), defender, null);
        if (damagedResult != null && damagedResult.originalDamage > 0 && myField.getCard(i) != null) {
            for (FeatureInfo featureInfo : myField.getCard(i).getNormalUsableFeatures()) {
                if (featureInfo.getFeature().getType() == FeatureType.横扫) {

                    List<CardInfo> sweepDefenders = new ArrayList<CardInfo>();
                    if (i > 0 && opField.getCard(i - 1) != null) {
                        sweepDefenders.add(opField.getCard(i - 1));
                    }
                    if (opField.getCard(i + 1) != null) {
                        sweepDefenders.add(opField.getCard(i + 1));
                    }

                    for (CardInfo sweepDefender : sweepDefenders) {
                        ui.useSkill(myField.getCard(i), sweepDefender, featureInfo.getFeature(), true);
                        resolver.attackCard(myField.getCard(i), sweepDefender, featureInfo, damagedResult.originalDamage);
                        // Physical attack cannot proceed if attacker is killed by counter attack skills.
                        if (myField.getCard(i) == null) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private Phase roundStart() throws GameOverSignal, AllCardsDieSignal, HeroDieSignal {
        if (this.stage.getRound() > stage.getRule().getMaxRound()) {
            throw new GameOverSignal();
        }
        Player player = this.getActivePlayer();
        this.stage.getResolver().deactivateRunes(player);
        this.stage.getResolver().removeOneRoundEffects(player);
        if (player.getDeck().size() == 0 && player.getField().size() == 0
                && player.getHand().size() == 0) {
            throw new AllCardsDieSignal(player);
        }

        this.stage.getUI().roundStarted(player, this.stage.getRound());
        int thresholdRound = 51;

        if (this.stage.getRound() >= thresholdRound) {
            int extraRound = this.stage.getRound() - thresholdRound;
            int heroDamage = 50 + extraRound * 30;
            Feature feature = Feature.自动扣血();
            this.stage.getResolver().attackHero(player, player, feature, heroDamage);
        }
        return Phase.抽卡;
    }

    private Phase drawCard() {
        Player activePlayer = this.getActivePlayer();
        Hand hand = activePlayer.getHand();
        if (hand.size() >= this.stage.getRule().getMaxHandCards()) {
            stage.getUI().cantDrawHandFull(activePlayer);
            return Phase.召唤;
        }
        Deck deck = activePlayer.getDeck();
        if (deck.isEmpty()) {
            stage.getUI().cantDrawDeckEmpty(activePlayer);
            return Phase.召唤;
        }
        CardInfo newCard = deck.draw();
        hand.addCard(newCard);
        stage.getUI().cardDrawed(activePlayer, newCard);
        return Phase.召唤;
    }
}