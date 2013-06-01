package cfvbaibai.cardfantasy;

import cfvbaibai.cardfantasy.engine.GameResult;

public class GameOverSignal extends Throwable {

    private static final long serialVersionUID = 2889047362689303130L;
    private GameResult result;
    
    public GameOverSignal(GameResult result) {
        if (result == null) {
            throw new CardFantasyRuntimeException("result cannot be null!");
        }
        this.result = result;
    }
    
    public GameResult getResult() {
        return this.result;
    }

}
