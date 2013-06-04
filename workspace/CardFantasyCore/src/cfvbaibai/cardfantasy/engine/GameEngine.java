package cfvbaibai.cardfantasy.engine;

import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameOverSignal;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.PlayerInfo;

public class GameEngine {

    private StageInfo stage;
    private Board getBoard() {
        return this.stage.getBoard();
    }

    public GameEngine(GameUI ui, Rule rule) {
        this.stage = new StageInfo(new Board(), ui, rule);
    }
    
    private Player getActivePlayer() {
        return this.stage.getActivePlayer();
    }
    
    private Player getInactivePlayer() {
        return this.stage.getInactivePlayers().get(0);
    }

    public void RegisterPlayers(PlayerInfo player1Info, PlayerInfo player2Info) {
        Player player1 = new Player(player1Info);
        getBoard().addPlayer(player1);
        stage.getUI().playerAdded(player1);
        Player player2 = new Player(player2Info);
        getBoard().addPlayer(player2);
        stage.getUI().playerAdded(player2);
    }

    public GameResult playGame() {
        this.stage.getUI().gameStarted(getBoard(), this.stage.getRule());
        this.stage.setActivePlayerNumber(0);
        this.stage.setRound(0);
        GameResult result = proceedGame();
        this.stage.getUI().gameEnded(result);
        return result;
    }

    private GameResult proceedGame() {
        Phase phase = Phase.Start;
        Phase nextPhase = Phase.Unknown;
        try {
            while (true) {
                if (phase == Phase.Start) {
                    nextPhase = roundStart();
                } else if (phase == Phase.Draw) {
                    nextPhase = drawCard();
                } else if (phase == Phase.Standby) {
                    nextPhase = standby();
                } else if (phase == Phase.Summon) {
                    nextPhase = summonCards();
                } else if (phase == Phase.Battle) {
                    nextPhase = battle();
                } else if (phase == Phase.End) {
                    nextPhase = roundEnd();
                } else {
                    throw new CardFantasyRuntimeException(String.format("Unknown phase encountered: %s", phase));
                }
                stage.getUI().phaseChanged(getActivePlayer(), phase, nextPhase);
                phase = nextPhase;
                nextPhase = Phase.Unknown;
            }
        } catch (GameOverSignal signal) {
            return new GameResult(this.getBoard(), this.getBoard().getPlayer(0), stage.getRound(), GameEndCause.TOO_LONG);
        } catch (HeroDieSignal signal) {
            return new GameResult(this.getBoard(), getOpponent(signal.getDeadPlayer()), this.stage.getRound(), GameEndCause.HERO_DIE);
        } catch (AllCardsDieSignal signal) {
            return new GameResult(this.getBoard(), getOpponent(signal.getDeadPlayer()), this.stage.getRound(), GameEndCause.ALL_CARDS_DIE);
        }
    }

    private Player getOpponent(Player player) {
        return this.getActivePlayer() == player ? this.getInactivePlayer() : this.getActivePlayer();
    }

    private Phase summonCards() {
        List<CardInfo> summonedCards = this.stage.getUI().summonCards(stage);
        Hand hand = this.getActivePlayer().getHand();
        Field field = this.getActivePlayer().getField();
        for (CardInfo summonedCard : summonedCards) {
            hand.removeCard(summonedCard);
            summonedCard.reset();
            field.addCard(summonedCard);
        }
        return Phase.Battle;
    }

    private Phase standby() {
        return Phase.Summon;
    }

    private Phase roundEnd() {
        Collection<CardInfo> allHandCards = this.getBoard().getAllHandCards();
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay > 0) {
                card.setSummonDelay(summonDelay - 1);
            }
        }

        Player previousPlayer = getActivePlayer();
        this.stage.getUI().roundEnded(previousPlayer, stage.getRound());
        this.stage.setRound(stage.getRound() + 1);
        int nextPlayerNumber = (this.stage.getActivePlayerNumber() + 1) % getBoard().getPlayerCount();
        this.stage.setActivePlayerNumber(nextPlayerNumber);
        Player nextPlayer = this.getActivePlayer();
        stage.getUI().playerChanged(previousPlayer, nextPlayer);
        return Phase.Start;
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
            
            CardStatus status = myField.getCard(i).getStatus();
            if (status.containsStatus(CardStatusType.±ù¶³) || status.containsStatus(CardStatusType.Ëø¶¨)) {
                ui.cannotAction(myField.getCard(i));
            } else {

                resolver.resolvePreAttackFeature(myField.getCard(i), getInactivePlayer());
                if (myField.getCard(i) == null) {
                    continue;
                }
                if (opField.getCard(i) == null) {
                    resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getAT());
                } else {

                    resolver.resolvePreAttackCardFeature(myField.getCard(i), opField.getCard(i));
                    if (myField.getCard(i) == null) {
                        continue;
                    }
                    if (opField.getCard(i) == null) {
                        resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getAT());
                    } else {
                        CardInfo defender = opField.getCard(i);
                        int damage = attackCard(myField.getCard(i), defender);

                        resolver.resolveExtraAttackFeature(myField.getCard(i), opField.getCard(i), getInactivePlayer(), damage);
                        if (myField.getCard(i) == null) {
                            continue;
                        }

                        resolver.resolveCounterAttackFeature(myField.getCard(i), defender, null);
                        // Remove lasting effects
                        resolver.removeEffects(myField.getCard(i), FeatureType.Ê¥¹â, FeatureType.±©»÷);
                    }
                }
                // 
                resolver.resolvePostAttackFeature(myField.getCard(i), getInactivePlayer());
            }

            if (myField.getCard(i) == null) {
                continue;
            }
            // Resolve POISON damage
            List<CardStatusItem> items = myField.getCard(i).getStatus().getStatusOf(CardStatusType.ÖÐ¶¾);
            for (CardStatusItem item : items) {
                ui.debuffDamage(myField.getCard(i), item, item.getEffect());
                resolver.applyDamage(myField.getCard(i), item.getEffect());
            }
        }

        myField.compact();
        opField.compact();
        
        for (CardInfo card : myField) {
            card.getStatus().remove(CardStatusType.±ù¶³);
            card.getStatus().remove(CardStatusType.Âé±Ô);
            card.getStatus().remove(CardStatusType.Ëø¶¨);
            card.getStatus().remove(CardStatusType.ÖÐ¶¾);
        }

        return Phase.End;
    }

    private int attackCard(CardInfo attacker, CardInfo defender) {
        this.stage.getUI().useSkill(attacker, defender, null);
        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingFeature(attacker, defender, null);
        if (!blockingResult.attackable) {
            return -1;
        }
        this.stage.getUI().attackCard(attacker, defender, null, blockingResult.damage);
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(defender,blockingResult.damage);
        if (damagedResult.cardDead) {
            stage.getResolver().resolveDyingFeature(attacker, defender, null);
        }
        return damagedResult.actualDamage;
    }

    private Phase roundStart() throws GameOverSignal, AllCardsDieSignal {
        if (this.stage.getRound() > stage.getRule().getMaxRound()) {
            throw new GameOverSignal();
        }
        if (this.getActivePlayer().getDeck().size() == 0 &&
            this.getActivePlayer().getField().size() == 0 &&
            this.getActivePlayer().getHand().size() == 0) {
            throw new AllCardsDieSignal(this.getActivePlayer());
        }

        this.stage.getUI().roundStarted(this.getActivePlayer(), this.stage.getRound());
        return Phase.Draw;
    }

    private Phase drawCard() {
        Player activePlayer = this.getActivePlayer();
        Hand hand = activePlayer.getHand();
        if (hand.size() >= this.stage.getRule().getMaxHandCards()) {
            stage.getUI().cantDrawHandFull(activePlayer);
            return Phase.Standby;
        }
        Deck deck = activePlayer.getDeck();
        if (deck.isEmpty()) {
            stage.getUI().cantDrawDeckEmpty(activePlayer);
            return Phase.Standby;
        }
        CardInfo newCard = deck.draw();
        newCard.resetSummonDelay();
        hand.addCard(newCard);
        stage.getUI().cardDrawed(activePlayer, newCard);
        return Phase.Standby;
    }
}
