package cfvbaibai.cardfantasy.engine;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Card;

public abstract class GameUI {

    private Board board;
    private Rule rule;
    
    protected GameUI() {
    }
    
    public void gameStarted(Board board, Rule rule) {
        this.board = board;
        this.rule = rule;
        this.gameStarted();
    }
    
    protected Board getBoard() {
        return this.board;
    }
    
    protected Rule getRule() {
        return this.rule;
    }
    
    protected abstract void gameStarted();
    
    public abstract void playerAdded(Player player);
    
    public abstract void errorHappened(CardFantasyRuntimeException e);

    public abstract void phaseChanged(Player player, Phase previous, Phase current);

    public abstract void playerChanged(Player previousPlayer, Player nextPlayer);
    
    public abstract void gameEnded(GameResult result);
    
    public abstract void cardDrawed(Player drawer, CardInfo card);

    public abstract void cantDrawDeckEmpty(Player drawer);
    
    public abstract void cantDrawHandFull(Player drawer);

    public abstract List<CardInfo> summonCards(Player player, int round);
    
    public abstract void roundStarted(Player player, int round);
    public abstract void roundEnded(Player player, int round);
}
