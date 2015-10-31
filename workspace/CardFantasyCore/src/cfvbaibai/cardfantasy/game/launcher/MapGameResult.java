package cfvbaibai.cardfantasy.game.launcher;

public class MapGameResult {
    private int timeoutCount;
    private int winCount;
    private int advWinCount;
    private int lostCount;
    private int unknownCount;
    private String validationResult;

    public int getTimeoutCount() {
        return timeoutCount;
    }
    public void setTimeoutCount(int timeoutCount) {
        this.timeoutCount = timeoutCount;
    }
    public int getWinCount() {
        return winCount;
    }
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
    public int getAdvWinCount() {
        return advWinCount;
    }
    public void setAdvWinCount(int advWinCount) {
        this.advWinCount = advWinCount;
    }
    public int getLostCount() {
        return lostCount;
    }
    public void setLostCount(int lostCount) {
        this.lostCount = lostCount;
    }
    public int getUnknownCount() {
        return unknownCount;
    }
    public void setUnknownCount(int unknownCount) {
        this.unknownCount = unknownCount;
    }
    public String getValidationResult() {
        return validationResult;
    }
    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }
    public String getLastResultName() {
        if (this.getAdvWinCount() != 0) {
            return "胜利，符合过关条件";
        }
        if (this.getWinCount() != 0) {
            return "胜利，不符合过关条件";
        }
        if (this.getLostCount() != 0) {
            return "失败";
        }
        if (this.getTimeoutCount() != 0) {
            return "战斗超时";
        }
        return "战斗出错";
    }
}
