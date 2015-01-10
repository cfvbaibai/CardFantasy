package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.battlestat.BattleStat;



public class GameResult {
    private Board finalBoard;
    private Player winner;
    private int round;
    private GameEndCause gameEndCause;
    // Only applicable to boss battle
    private int damageToBoss;
    private int killedGuardCount;
    private BattleStat battleStat;

    public GameResult(
        Board finalBoard, Player winner, int round,
        GameEndCause gameEndCause, int damageToBoss, int killedGuardCount, 
        BattleStat battleStat) {
        this.finalBoard = finalBoard;
        this.winner = winner;
        this.round = round;
        this.gameEndCause = gameEndCause;
        this.damageToBoss = damageToBoss;
        this.killedGuardCount = killedGuardCount;
        this.battleStat = battleStat;
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
    
    public Player getLoser() {
        for (Player player : finalBoard.getPlayers()) {
            if (!player.getId().equals(this.winner.getId())) {
                return player;
            }
        }
        return null;
    }

    public GameEndCause getCause() {
        return this.gameEndCause;
    }
    
    public int getDamageToBoss() {
        return this.damageToBoss;
    }
    
    public BattleStat getBattleStat() {
        return this.battleStat;
    }

    public int getKilledGuardCount() {
        return this.killedGuardCount;
    }
}
