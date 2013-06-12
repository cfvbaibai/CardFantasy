package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameOverSignal;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.PlayerInfo;

public class GameEngine {

    private StageInfo stage;

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
        stage.addPlayer(player1Info);
        stage.addPlayer(player2Info);
    }

    public GameResult playGame() {
        this.stage.gameStarted();
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
            return stage.result(this.stage.getPlayers().get(0), GameEndCause.TOO_LONG);
        } catch (HeroDieSignal signal) {
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.HERO_DIE);
        } catch (AllCardsDieSignal signal) {
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.ALL_CARDS_DIE);
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
        return Phase.Standby;
    }

    private Phase standby() throws HeroDieSignal {
        this.stage.getResolver().activateRunes(this.getActivePlayer(), this.getInactivePlayer());
        this.stage.getResolver().resolvePreAttackRune(this.getActivePlayer(), this.getInactivePlayer());
        return Phase.Battle;
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
            if (status.containsStatus(CardStatusType.±˘∂≥) || status.containsStatus(CardStatusType.À¯∂®)
                    || status.containsStatus(CardStatusType.–È»ı)) {
                ui.cannotAction(myField.getCard(i));
            } else if (status.containsStatus(CardStatusType.√‘ªÛ)) {
                ui.confused(myField.getCard(i));
            } else {
                tryAttackEnemy(myField, opField, i);
            }

            resolver.resolveDebuff(myField.getCard(i), CardStatusType.÷–∂æ);
            resolver.resolveDebuff(myField.getCard(i), CardStatusType.»º…’);

            // ªÿ¥∫
            resolver.resolveCardRoundEndingFeature(myField.getCard(i));
        }

        this.stage.getUI().compactField(myField);
        myField.compact();
        this.stage.getUI().compactField(opField);
        opField.compact();

        for (CardInfo card : myField.getAliveCards()) {
            card.getStatus().remove(CardStatusType.±˘∂≥);
            card.getStatus().remove(CardStatusType.¬È±‘);
            card.getStatus().remove(CardStatusType.À¯∂®);
            card.getStatus().remove(CardStatusType.÷–∂æ);
            card.getStatus().remove(CardStatusType.–È»ı);
            card.getStatus().remove(CardStatusType.√‘ªÛ);
        }

        return Phase.End;
    }

    private void tryAttackEnemy(Field myField, Field opField, int i) throws HeroDieSignal {
        FeatureResolver resolver = this.stage.getResolver();
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
        resolver.resolvePreAttackCardFeature(myField.getCard(i), opField.getCard(i));
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
            if (featureInfo.getFeature().getType() == FeatureType.∫·…®) {
                ui.useSkill(myField.getCard(i), defender, featureInfo.getFeature());
            }
        }
        int damage = resolver.attackCard(myField.getCard(i), defender);
        if (damage > 0 && myField.getCard(i) != null) {
            for (FeatureInfo featureInfo : myField.getCard(i).getNormalUsableFeatures()) {
                if (featureInfo.getFeature().getType() == FeatureType.∫·…®) {

                    List<CardInfo> sweepDefenders = new ArrayList<CardInfo>();
                    if (i > 0 && opField.getCard(i - 1) != null) {
                        sweepDefenders.add(opField.getCard(i - 1));
                    }
                    if (opField.getCard(i + 1) != null) {
                        sweepDefenders.add(opField.getCard(i + 1));
                    }

                    for (CardInfo sweepDefender : sweepDefenders) {
                        ui.useSkill(myField.getCard(i), sweepDefender, featureInfo.getFeature());
                        resolver.attackCard(myField.getCard(i), sweepDefender);
                        // Physical attack cannot proceed if attacker is killed by counter attack skills.
                        if (myField.getCard(i) == null) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private Phase roundStart() throws GameOverSignal, AllCardsDieSignal {
        if (this.stage.getRound() > stage.getRule().getMaxRound()) {
            throw new GameOverSignal();
        }
        this.stage.getResolver().deactivateRunes(this.getActivePlayer());
        if (this.getActivePlayer().getDeck().size() == 0 && this.getActivePlayer().getField().size() == 0
                && this.getActivePlayer().getHand().size() == 0) {
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
            return Phase.Summon;
        }
        Deck deck = activePlayer.getDeck();
        if (deck.isEmpty()) {
            stage.getUI().cantDrawDeckEmpty(activePlayer);
            return Phase.Summon;
        }
        CardInfo newCard = deck.draw();
        hand.addCard(newCard);
        stage.getUI().cardDrawed(activePlayer, newCard);
        return Phase.Summon;
    }
}
