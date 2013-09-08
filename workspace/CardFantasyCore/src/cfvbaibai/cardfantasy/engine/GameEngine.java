package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameOverSignal;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.PlayerInfo;

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

    public void RegisterPlayers(PlayerInfo player1Info, PlayerInfo player2Info) {
        stage.addPlayer(player1Info);
        stage.addPlayer(player2Info);
    }

    public GameResult playGame() {
        this.stage.gameStarted();
        this.stage.setRound(0);
        GameResult result = proceedGame();
        this.stage.getUI().gameEnded(result);
        return result;
    }

    private GameResult proceedGame() {
        Phase phase = Phase.¿ªÊ¼;
        Phase nextPhase = Phase.Î´Öª;
        try {
            while (true) {
                if (phase == Phase.¿ªÊ¼) {
                    nextPhase = roundStart();
                } else if (phase == Phase.³é¿¨) {
                    nextPhase = drawCard();
                } else if (phase == Phase.×¼±¸) {
                    nextPhase = standby();
                } else if (phase == Phase.ÕÙ»½) {
                    nextPhase = summonCards();
                } else if (phase == Phase.Õ½¶·) {
                    nextPhase = battle();
                } else if (phase == Phase.½áÊø) {
                    nextPhase = roundEnd();
                } else {
                    throw new CardFantasyRuntimeException(String.format("Unknown phase encountered: %s", phase));
                }
                stage.getUI().phaseChanged(getActivePlayer(), phase, nextPhase);
                phase = nextPhase;
                nextPhase = Phase.Î´Öª;
            }
        } catch (GameOverSignal signal) {
            return stage.result(this.stage.getPlayers().get(0), GameEndCause.Õ½¶·³¬Ê±);
        } catch (HeroDieSignal signal) {
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.Ó¢ÐÛËÀÍö);
        } catch (AllCardsDieSignal signal) {
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.¿¨Æ¬È«Ãð);
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
            this.stage.getResolver().summonCard(player, summonedCard);
        }

        player.getField().compact();
        this.getInactivePlayer().getField().compact();
        return Phase.×¼±¸;
    }

    private Phase standby() throws HeroDieSignal {
        this.stage.getResolver().activateRunes(this.getActivePlayer(), this.getInactivePlayer());
        this.stage.getResolver().resolvePreAttackRune(this.getActivePlayer(), this.getInactivePlayer());
        return Phase.Õ½¶·;
    }

    private Phase roundEnd() {
        Collection<CardInfo> allHandCards = this.stage.getAllHandCards();
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay > 0) {
                card.setSummonDelay(summonDelay - 1);
            }
        }
        
        for (CardInfo card : this.getActivePlayer().getField().toList()) {
            card.setFirstRound(false);
        }

        Player previousPlayer = getActivePlayer();
        this.stage.getUI().roundEnded(previousPlayer, stage.getRound());
        this.stage.setRound(stage.getRound() + 1);
        int nextPlayerNumber = (this.stage.getActivePlayerNumber() + 1) % stage.getPlayerCount();
        this.stage.setActivePlayerNumber(nextPlayerNumber);
        Player nextPlayer = this.getActivePlayer();
        stage.getUI().playerChanged(previousPlayer, nextPlayer);
        return Phase.¿ªÊ¼;
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
        for (int i = 0; i < myField.size(); ++i) {
            if (myField.getCard(i) == null) {
                continue;
            }

            CardInfo card = myField.getCard(i);
            ui.cardActionBegins(card);
            CardStatus status = myField.getCard(i).getStatus();
            if (status.containsStatus(CardStatusType.±ù¶³) || status.containsStatus(CardStatusType.Ëø¶¨)
                    || status.containsStatus(CardStatusType.ÐéÈõ)) {
                ui.cannotAction(myField.getCard(i));
            } else if (status.containsStatus(CardStatusType.ÃÔ»ó)) {
                ui.confused(myField.getCard(i));
                resolver.attackHero(myField.getCard(i), getActivePlayer(), null, myField.getCard(i).getAT());
            } else {
                tryAttackEnemy(myField, opField, i);
            }

            resolver.resolveDebuff(myField.getCard(i), CardStatusType.ÖÐ¶¾);
            resolver.resolveDebuff(myField.getCard(i), CardStatusType.È¼ÉÕ);

            // »Ø´º
            resolver.resolveCardRoundEndingFeature(myField.getCard(i));
            ui.cardActionEnds(card);
        }

        this.stage.getUI().compactField(myField);
        myField.compact();
        this.stage.getUI().compactField(opField);
        opField.compact();

        for (CardInfo card : myField.getAliveCards()) {
            card.getStatus().remove(CardStatusType.±ù¶³);
            card.getStatus().remove(CardStatusType.Âé±Ô);
            card.getStatus().remove(CardStatusType.Ëø¶¨);
            card.getStatus().remove(CardStatusType.ÖÐ¶¾);
            card.getStatus().remove(CardStatusType.ÐéÈõ);
            card.getStatus().remove(CardStatusType.ÃÔ»ó);
        }

        return Phase.½áÊø;
    }

    private void tryAttackEnemy(Field myField, Field opField, int i) throws HeroDieSignal {
        FeatureResolver resolver = this.stage.getResolver();
        resolver.resolvePreAttackCardFeature(myField.getCard(i), opField.getCard(i), true);
        resolver.resolvePreAttackFeature(myField.getCard(i), getInactivePlayer());
        if (myField.getCard(i) == null) {
            return;
        }
        if (opField.getCard(i) == null) {
            resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getAT());
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
            resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getAT());
        } else {
            processAttackCard(myField, opField, i);
        }
    }

    private void processAttackCard(Field myField, Field opField, int i) throws HeroDieSignal {
        CardInfo defender = opField.getCard(i);
        FeatureResolver resolver = this.stage.getResolver();
        GameUI ui = this.stage.getUI();
        for (FeatureInfo featureInfo : myField.getCard(i).getNormalUsableFeatures()) {
            if (featureInfo.getFeature().getType() == FeatureType.ºáÉ¨) {
                ui.useSkill(myField.getCard(i), defender, featureInfo.getFeature(), true);
            }
        }
        OnDamagedResult damagedResult = resolver.attackCard(myField.getCard(i), defender);
        if (damagedResult != null && damagedResult.originalDamage > 0 && myField.getCard(i) != null) {
            for (FeatureInfo featureInfo : myField.getCard(i).getNormalUsableFeatures()) {
                if (featureInfo.getFeature().getType() == FeatureType.ºáÉ¨) {

                    List<CardInfo> sweepDefenders = new ArrayList<CardInfo>();
                    if (i > 0 && opField.getCard(i - 1) != null) {
                        sweepDefenders.add(opField.getCard(i - 1));
                    }
                    if (opField.getCard(i + 1) != null) {
                        sweepDefenders.add(opField.getCard(i + 1));
                    }

                    for (CardInfo sweepDefender : sweepDefenders) {
                        ui.useSkill(myField.getCard(i), sweepDefender, featureInfo.getFeature(), true);
                        resolver.attackCard(myField.getCard(i), sweepDefender, damagedResult.originalDamage);
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
            Feature feature = Feature.×Ô¶¯¿ÛÑª();
            this.stage.getResolver().attackHero(player, player, feature, heroDamage);
        }
        return Phase.³é¿¨;
    }

    private Phase drawCard() {
        Player activePlayer = this.getActivePlayer();
        Hand hand = activePlayer.getHand();
        if (hand.size() >= this.stage.getRule().getMaxHandCards()) {
            stage.getUI().cantDrawHandFull(activePlayer);
            return Phase.ÕÙ»½;
        }
        Deck deck = activePlayer.getDeck();
        if (deck.isEmpty()) {
            stage.getUI().cantDrawDeckEmpty(activePlayer);
            return Phase.ÕÙ»½;
        }
        CardInfo newCard = deck.draw();
        hand.addCard(newCard);
        stage.getUI().cardDrawed(activePlayer, newCard);
        return Phase.ÕÙ»½;
    }
}
