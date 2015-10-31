package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;

public class GameResultStat {

    private int timeoutCount;
    private int p1WinCount;
    private int p2WinCount;
    private int conditionMetCount;
    
    private PlayerInfo p1;
    private PlayerInfo p2;
    private Rule rule;

    private GameResult lastResult;

    public GameResultStat(PlayerInfo p1, PlayerInfo p2, Rule rule) {
        p1WinCount = 0;
        p2WinCount = 0;
        timeoutCount = 0;
        this.p1 = p1;
        this.p2 = p2;
        this.rule = rule;
    }
    
    public void addResult(GameResult result) {
        String winnerId = result.getWinner().getId();
        if (result.getCause() == GameEndCause.战斗超时) {
            ++this.timeoutCount;
        } else if (winnerId.equals(p1.getId())) {
            ++this.p1WinCount;
        } else if (winnerId.equals(p2.getId())){
            ++this.p2WinCount;
        } else {
            throw new CardFantasyRuntimeException("Invalid result! Unknown winner: " + winnerId);
        }
        if (rule.getCondition() != null && rule.getCondition().meetCriteria(result) && winnerId.equals(p1.getId())) {
            ++this.conditionMetCount;
        }
        this.lastResult = result;
    }
    
    public int count() {
        return this.p1WinCount + this.p2WinCount;
    }
    
    public int getP1WinRate() {
        return this.p1WinCount * 100 / this.count();
    }
    
    public int getP2WinRate() {
        return this.p2WinCount * 100 / this.count();
    }
    
    public int getP1Win() {
        return this.p1WinCount;
    }
    
    public int getP2Win() {
        return this.p2WinCount;
    }
    
    public PlayerInfo getP1() {
        return this.p1;
    }
    
    public PlayerInfo getP2() {
        return this.p2;
    }
    
    public int getTimeoutCount() {
        return this.timeoutCount;
    }
    
    public int getTimeoutRate() {
        return this.timeoutCount * 100 / this.count();
    }

    public int getConditionMet() {
        return this.conditionMetCount;
    }
    
    public GameResult getLastResult() {
        return this.lastResult;
    }
}
