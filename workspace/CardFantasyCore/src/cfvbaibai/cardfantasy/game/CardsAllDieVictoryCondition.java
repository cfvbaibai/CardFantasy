package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;

public class CardsAllDieVictoryCondition extends VictoryCondition {
    @Override
    public boolean meetCriteria(GameResult result) {
        Player loser = result.getLoser();
        return loser.getDeck().size() == 0 && loser.getHand().size() == 0 && loser.getField().getAliveCards().size() == 0; 
    }

    @Override
    public String getDescription() {
        return "对方卡牌全灭";
    }
}