package cfvbaibai.cardfantasy.test;

import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
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
    public List<CardInfo> summonCards(Player player, int round) {
        List<CardInfo> summonedCards = new LinkedList<CardInfo>();
        for (CardInfo card : player.getHand()) {
            if (card.getSummonDelay() == 0) {
                summonedCards.add(card);
                sayF("<%s> summons card: <%s>", player.getId(), card.getCard().getName());
            }
        }
        return summonedCards;
    }
    
    @Override
    public void attackCard(CardInfo attacker, CardInfo defender, int damage) {
        sayF("%s attacks %s. Damage: %d", attacker.getShortDesc(), defender.getShortDesc(), damage);
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
        showCards(grave, "Grave");
    }

    private void showField(Field field) {
        showCards(field, "Field");
    }

    private void showHand(Hand hand) {
        showCards(hand, "Hand");

    }

    private void showDeck(Deck deck) {
        showCards(deck, "Deck");
    }

    private void showCards(Iterable<CardInfo> cards, String title) {
        StringBuffer sb = new StringBuffer();
        sb.append(title);
        sb.append(": ");
        for (CardInfo card : cards) {
            sb.append(String.format("%s (AT=%d, HP=%d, SD=%d), ", card.getCard().getName(), card.getAT(), card.getHP(), card.getSummonDelay()));
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
