package cfvbaibai.cardfantasy.test;

import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.Board;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Deck;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.Hand;
import cfvbaibai.cardfantasy.engine.Phase;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.StageInfo;

public class TestGameUI extends GameUI {
    
    public TestGameUI() {
    }

    @Override
    public void playerAdded(Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void roundStarted(Player player, int round) {
        say("================================================================================================================================");
        sayF("================================ Round %d started for player <%s> ================================", round, player.getId());
        say("================================================================================================================================");
    }

    @Override
    public void roundEnded(Player player, int round) {
        sayF("Round %d ended for player <%s>", round, player.getId());
    }
    
    @Override
    public void errorHappened(CardFantasyRuntimeException e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void phaseChanged(Player player, Phase previous, Phase current) {
        sayF("Phase Changed: %s => %s", previous.name().toUpperCase(), current.name().toUpperCase());
    }

    @Override
    public void playerChanged(Player previousPlayer, Player nextPlayer) {
        sayF("Player Changed: <%s> => <%s>", previousPlayer.getId(), nextPlayer.getId());
    }

    @Override
    public void cardDrawed(Player drawer, CardInfo card) {
        sayF("<%s> draws a card: <%s>", drawer.getId(), card.getCard().getName());
        showBoard();
    }

    @Override
    public void cantDrawDeckEmpty(Player drawer) {
        sayF("Player <%s>'s deck is empty. Cannot draw card.", drawer.getId());
        this.showBoard();
}

    @Override
    public void cantDrawHandFull(Player drawer) {
        sayF("Player <%s>'s hand is full. Cannot draw card.", drawer.getId());
        this.showBoard();
    }

    @Override
    public List<CardInfo> summonCards(StageInfo stage) {
        List<CardInfo> summonedCards = new LinkedList<CardInfo>();
        Player player = stage.getActivePlayer();
        for (CardInfo card : player.getHand()) {
            if (card.getSummonDelay() == 0) {
                summonedCards.add(card);
                sayF("<%s> summons card: <%s>", player.getId(), card.getCard().getName());
            }
        }
        return summonedCards;
    }
    
    @Override
    public void attackCard(CardInfo attacker, CardInfo defender, Feature feature, int damage) {
        if (feature == null) {
        sayF("%s attacks %s. Damage: %d", attacker.getShortDesc(), defender.getShortDesc(), damage);
        } else {
            sayF("%s attacks %s by [[%s]]. Damage: %d", attacker.getShortDesc(), defender.getShortDesc(), feature.getShortDesc(), damage);
        }
    }

    @Override
    public void cardDead(CardInfo deadCard) {
        sayF("%s dies!", deadCard.getShortDesc());
    }

    @Override
    public void attackHero(CardInfo attacker, Player hero, int damage) {
        sayF("%s attacks <%s> directly! Damage: %d", attacker.getShortDesc(), hero.getId(), damage);
    }
    
    @Override
    public void gameEnded(GameResult result) {
        sayF("Game ended. Winner: <%s>, GameEndCause: %s", result.getWinner().getId(), result.getCause().toString());
    }

    @Override
    protected void gameStarted() {
        say("Game started!");
        sayF("Rule: MaxHandCard=%d, MaxRound=%d", getRule().getMaxHandCards(), getRule().getMaxRound());
        this.showBoard();
    }

    private void showBoard() {
        Board board = this.getBoard();
        say("-----------------------------------------------------------------------------");
        for (int i = 0; i < board.getPlayerCount(); ++i) {
            Player player = board.getPlayer(i);
            sayF("Player %d: %s - Life: %d", i, player.getId(), player.getLife());
            showDeck(player.getDeck());
            showHand(player.getHand());
            showField(player.getField());
            showGrave(player.getGrave());
            say("");
        }
        say("-----------------------------------------------------------------------------");
    }

    private void showGrave(Grave grave) {
        StringBuffer sb = new StringBuffer();
        sb.append("Grave: ");
        for (CardInfo card : grave) {
            sb.append(String.format("%s (LV=%d, IAT=%d, MHP=%d), ",
                    card.getCard().getName(), card.getCard().getLevel(), card.getCard().getInitAT(), card.getCard().getMaxHP()));
        }
        say(sb.toString());
    }

    private void showField(Field field) {
        StringBuffer sb = new StringBuffer();
        sb.append("Field: ");
        for (CardInfo card : field) {
            sb.append(String.format("%s (LV=%d, AT=%d/%d, HP=%d/%d, ST=%s), ",
                    card.getCard().getName(), card.getCard().getLevel(), card.getAT(), card.getCard().getInitAT(),
                    card.getHP(), card.getCard().getMaxHP(), card.getStatus().getType()));
        }
        say(sb.toString());
    }

    private void showHand(Hand hand) {
        StringBuffer sb = new StringBuffer();
        sb.append("Hand: ");
        for (CardInfo card : hand) {
            sb.append(String.format("%s (LV=%d, IAT=%d, MHP=%d, SD=%d), ",
                    card.getCard().getName(), card.getCard().getLevel(), card.getCard().getInitAT(), card.getCard().getMaxHP(), card.getSummonDelay()));
        }
        say(sb.toString());
    }

    private void showDeck(Deck deck) {
        StringBuffer sb = new StringBuffer();
        sb.append("Deck: ");
        for (CardInfo card : deck) {
            sb.append(String.format("%s (LV=%d, IAT=%d, MHP=%d), ",
                    card.getCard().getName(), card.getCard().getLevel(), card.getCard().getInitAT(), card.getCard().getMaxHP()));
        }
        say(sb.toString());
    }

    private static void sayF(String format, Object... args) {
        say(String.format(format, args));
    }

    private static void say(Object obj) {
        System.out.println(obj.toString());
    }
}
