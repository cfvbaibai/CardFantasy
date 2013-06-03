package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

public class StageInfo {
    private Board board;
    private int activePlayerNumber;
    private Phase phase;
    private int round;
    private GameUI ui;
    private Rule rule;
    private FeatureResolver resolver;
    
    public StageInfo(Board board, GameUI ui, Rule rule) {
        this.board = board;
        this.activePlayerNumber = -1;
        this.phase = Phase.Unknown;
        this.round = -1;
        this.ui = ui;
        this.rule = rule;
        this.resolver = new FeatureResolver(this);
    }
    
    public FeatureResolver getResolver() {
        return this.resolver;
    }
    
    public int getActivePlayerNumber() {
        return activePlayerNumber;
    }

    public void setActivePlayerNumber(int activePlayerNumber) {
        this.activePlayerNumber = activePlayerNumber;
    }
    
    public Player getActivePlayer() {
        return this.getBoard().getPlayer(this.getActivePlayerNumber());
    }
    
    public List<Player> getInactivePlayers() {
        List<Player> inactivePlayers = new ArrayList<Player>(this.getBoard().getPlayerCount() - 1);
        for (int i = 0; i < this.getBoard().getPlayerCount(); ++i) {
            if (i != this.getActivePlayerNumber()) {
                inactivePlayers.add(this.getBoard().getPlayer(i));
            }
        }
        return inactivePlayers;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public GameUI getUI() {
        return this.ui;
    }

    public void setUI(GameUI ui) {
        this.ui = ui;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}