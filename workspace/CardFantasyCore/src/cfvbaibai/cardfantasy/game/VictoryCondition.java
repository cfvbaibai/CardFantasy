package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;

public abstract class VictoryCondition {
    public abstract boolean meetCriteria(GameResult result);
    
    public static VictoryCondition parse(String desc) {
        if (desc == null) {
            throw new CardFantasyRuntimeException("desc should not be null");
        }
        if (desc.equalsIgnoreCase("Any")) {
            return new DummyVictoryCondition();
        } else if (desc.equalsIgnoreCase("EnemyCardsAllDie")) {
            return new CardsAllDieVictoryCondition();
        } else if (desc.startsWith("MyHeroHP:")) {
            int hpThreshold = Integer.parseInt(desc.substring(9));
            return new HeroHPVictoryCondition(hpThreshold);
        } else if (desc.equalsIgnoreCase("NoRune")) {
            return new NoRuneVictoryCondition();
        } else {
            throw new CardFantasyRuntimeException("Invalid victory condition desc: " + desc);
        }
    }
}

class DummyVictoryCondition extends VictoryCondition {

    @Override
    public boolean meetCriteria(GameResult result) {
        return true;
    }
    
}

class CardsAllDieVictoryCondition extends VictoryCondition {

    public CardsAllDieVictoryCondition() {
    }

    @Override
    public boolean meetCriteria(GameResult result) {
        return result.getCause() == GameEndCause.卡片全灭; 
    }
    
}

class HeroHPVictoryCondition extends VictoryCondition {

    private int threshold;
    
    public HeroHPVictoryCondition(int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public boolean meetCriteria(GameResult result) {
        Player winner = result.getWinner();
        return winner.getHP() * 100 / winner.getMaxHP() > threshold;
    }
    
}

class NoRuneVictoryCondition extends VictoryCondition {

    @Override
    public boolean meetCriteria(GameResult result) {
        return result.getWinner().getRuneBox().getRunes().isEmpty();
    }
    
}