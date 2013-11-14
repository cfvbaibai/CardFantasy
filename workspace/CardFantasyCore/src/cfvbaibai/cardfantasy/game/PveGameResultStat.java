package cfvbaibai.cardfantasy.game;

import java.util.HashMap;
import java.util.Map;

public class PveGameResultStat {
    private Map <PveGameResult, Integer> results;
    
    public PveGameResultStat() {
        this.results = new HashMap<PveGameResult, Integer>();
    }
    
    public void addResult(PveGameResult result) {
        Integer stat = results.get(result);
        if (stat == null) {
            this.results.put(result, 1);
        } else {
            this.results.put(result, stat + 1);
        }
    }
    
    public int getStat(PveGameResult result) {
        Integer stat = results.get(result);
        if (stat == null) {
            return 0;
        } else {
            return stat;
        }
    }
    
    public int count() {
        return getStat(PveGameResult.BASIC_WIN) + getStat(PveGameResult.ADVANCED_WIN) + getStat(PveGameResult.LOSE);
    }
    
    public int getAllWinRate() {
        int winStat = getStat(PveGameResult.BASIC_WIN) + getStat(PveGameResult.ADVANCED_WIN);
        return winStat * 100 / count(); 
    }
    
    public int getAdvWinRate() {
        int winStat = getStat(PveGameResult.ADVANCED_WIN);
        return winStat * 100 / count();
    }

    public int countLost() {
        return getStat(PveGameResult.LOSE);
    }
    
    public int countWin() {
        return getStat(PveGameResult.BASIC_WIN);
    }
    
    public int countAdvWin() {
        return getStat(PveGameResult.ADVANCED_WIN);
    }

    public int countAllWin() {
        return countWin() + countAdvWin();
    }
    
    public int countTimeout() {
        return getStat(PveGameResult.TIMEOUT);
    }
    
    public int countUnknown() {
        return getStat(PveGameResult.UNKNOWN);
    }
}
