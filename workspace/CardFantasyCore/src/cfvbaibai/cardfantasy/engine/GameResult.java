package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.PlayerInfo;

public class GameResult {
    private Board finalBoard;
    private int winnerNumber;
    private int round;
    private Cause cause;

    public GameResult(Board finalBoard, int winnerNumber, int round, Cause cause) {
        this.finalBoard = finalBoard;
        this.winnerNumber = winnerNumber;
        this.round = round;
        this.cause = cause;
    }

    public Player getWinner() {
        return this.finalBoard.getPlayer(this.winnerNumber);
    }
}
