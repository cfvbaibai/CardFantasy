package cfvbaibai.cardfantasy.game.launcher;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.game.GameResultStat;

public class ArenaGameResult {
    private GameResultStat stat;
    private int gameCount;
    private PlayerInfo player1;
    private PlayerInfo player2;
    private String deckValidationResult;
    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
    public int getGameCount() {
        return gameCount;
    }
    public PlayerInfo getPlayer1() {
        return player1;
    }
    public void setPlayer1(PlayerInfo player1) {
        this.player1 = player1;
    }
    public PlayerInfo getPlayer2() {
        return player2;
    }
    public void setPlayer2(PlayerInfo player2) {
        this.player2 = player2;
    }
    public String getDeckValidationResult() {
        return deckValidationResult;
    }
    public void setDeckValidationResult(String deckValidationResult) {
        this.deckValidationResult = deckValidationResult;
    }
    public GameResultStat getStat() {
        return stat;
    }
    public void setStat(GameResultStat stat) {
        this.stat = stat;
    }
}
