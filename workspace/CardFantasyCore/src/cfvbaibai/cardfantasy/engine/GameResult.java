package cfvbaibai.cardfantasy.engine;


public class GameResult {
    private Board finalBoard;
    private Player winner;
    private int round;
    private GameEndCause gameEndCause;

    public GameResult(Board finalBoard, Player winner, int round, GameEndCause gameEndCause) {
        this.finalBoard = finalBoard;
        this.winner = winner;
        this.round = round;
        this.gameEndCause = gameEndCause;
    }
    
    public int getRound() {
        return this.round;
    }
    
    public Board getFinalBoard() {
        return this.finalBoard;
    }

    public Player getWinner() {
        return winner;
    }

    public GameEndCause getCause() {
        return this.gameEndCause;
    }
}
