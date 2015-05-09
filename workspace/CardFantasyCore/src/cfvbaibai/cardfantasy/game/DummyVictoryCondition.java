package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.engine.GameResult;

public class DummyVictoryCondition extends VictoryCondition {
    @Override
    public boolean meetCriteria(GameResult result) {
        return true;
    }

    @Override
    public String getDescription() {
        return "战斗胜利";
    }
}