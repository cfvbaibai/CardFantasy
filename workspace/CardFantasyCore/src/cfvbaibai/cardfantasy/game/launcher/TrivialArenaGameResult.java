package cfvbaibai.cardfantasy.game.launcher;

public class TrivialArenaGameResult {
    public String validationResult;
    public int p1Win;
    public int p2Win;
    public int timeoutCount;
    public TrivialArenaGameResult(ArenaGameResult result) {
        this.validationResult = result.getDeckValidationResult();
        this.p1Win = result.getStat().getP1Win();
        this.p2Win = result.getStat().getP2Win();
        this.timeoutCount = result.getStat().getTimeoutCount();
    }
}