package cfvbaibai.cardfantasy.game;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneClass;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public abstract class VictoryCondition {
    public abstract boolean meetCriteria(GameResult result);
    
    public static VictoryCondition parse(String desc) {
        if (desc == null) {
            throw new CardFantasyRuntimeException("desc should not be null");
        }
        if (desc.equalsIgnoreCase("Any")) {
            return new DummyVictoryCondition();
        } else if (desc.equalsIgnoreCase("EnemyAllCardsDie")) {
            return new CardsAllDieVictoryCondition();
        } else if (desc.startsWith("MyHeroHP:")) {
            int hpThreshold = Integer.parseInt(desc.substring(9));
            return new HeroHPVictoryCondition(hpThreshold);
        } else if (desc.startsWith("Round:")) {
            int maxRound = Integer.parseInt(desc.substring(6));
            return new RoundVictoryCondition(maxRound);
        } else if (desc.equals("EnemyHeroDie")) {
            return new EnemyHeroDieVictoryCondition();
        } else if (desc.startsWith("MyDeadCard:")) {
            int maxDeadCard = Integer.parseInt(desc.substring(11));
            return new MaxDeadCardVictoryCondition(maxDeadCard);
        } else if (desc.startsWith("CardOfStar:")) {
            String rest = desc.substring(11);
            String[] parts = rest.split(":");
            int star = Integer.parseInt(parts[0]);
            int minCount = Integer.parseInt(parts[1]);
            return new CardOfStarVictoryCondition(star, minCount);
        } else if (desc.startsWith("CardOfRace:")) {
            String rest = desc.substring(11);
            String[] parts = rest.split(":");
            Race race = Race.BOSS;
            if (parts[0].equals("K")) {
                race = Race.KINGDOM;
            } else if (parts[0].equals("F")) {
                race = Race.FOREST;
            } else if (parts[0].equals("S")) {
                race = Race.SAVAGE;
            } else if (parts[0].equals("H")) {
                race = Race.HELL;
            } else {
                throw new CardFantasyRuntimeException("Invalid race definition in CardOfRace victory condition: " + parts[0]);
            }
            int minCount = Integer.parseInt(parts[1]);
            return new CardOfRaceVictoryCondition(race, minCount);
        } else if (desc.startsWith("NoRune:")) {
            String rest = desc.substring(7);
            return new NoRuneVictoryCondition(toRuneClass(rest));
        } else if (desc.startsWith("HasRune:")) {
            String rest = desc.substring(8);
            return new HasRuneVictoryCondition(toRuneClass(rest));
        } else {
            throw new CardFantasyRuntimeException("Invalid victory condition desc: " + desc);
        }
    }
    
    private static RuneClass toRuneClass(String shorthand) {
        if (shorthand == null) {
            return null;
        }
        RuneClass runeClass = null;
        if (shorthand.equals("A")) {
            runeClass = null;
        } else if (shorthand.equals("G")) {
            runeClass = RuneClass.GROUND;
        } else if (shorthand.equals("F")) {
            runeClass = RuneClass.FIRE;
        } else if (shorthand.equals("I")) {
            runeClass = RuneClass.WATER;
        } else if (shorthand.equals("W")) {
            runeClass = RuneClass.WIND;
        }
        return runeClass;
    }

    public abstract String getDescription();
}

class DummyVictoryCondition extends VictoryCondition {
    @Override
    public boolean meetCriteria(GameResult result) {
        return true;
    }

    @Override
    public String getDescription() {
        return "战斗胜利";
    }
}

class CardsAllDieVictoryCondition extends VictoryCondition {
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

class HeroHPVictoryCondition extends VictoryCondition {
    private int threshold;
    
    public HeroHPVictoryCondition(int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public boolean meetCriteria(GameResult result) {
        Player winner = result.getWinner();
        return winner.getHP() * 100 / winner.getMaxHP() >= threshold;
    }

    @Override
    public String getDescription() {
        return "胜利时，己方英雄生命值不低于" + threshold + "%";
    }
}

class RoundVictoryCondition extends VictoryCondition {
    private int maxRound;
    
    public RoundVictoryCondition(int maxRound) {
        this.maxRound = maxRound;
    }
    
    @Override
    public boolean meetCriteria(GameResult result) {
        return result.getRound() < this.maxRound;
    }

    @Override
    public String getDescription() {
        return maxRound + "回合数内取得胜利";
    }
}

class EnemyHeroDieVictoryCondition extends VictoryCondition {
    @Override
    public boolean meetCriteria(GameResult result) {
        return result.getCause() == GameEndCause.英雄死亡;
    }

    @Override
    public String getDescription() {
        return "对方英雄阵亡";
    }
}

class MaxDeadCardVictoryCondition extends VictoryCondition {
    private int maxDeadCard;
    public MaxDeadCardVictoryCondition(int maxDeadCard) {
        this.maxDeadCard = maxDeadCard;
    }
    public boolean meetCriteria(GameResult result) {
        return result.getWinner().getGrave().size() < this.maxDeadCard;
    }
    @Override
    public String getDescription() {
        return "己方阵亡卡牌小于" + maxDeadCard + "张";
    }
}

class CardOfStarVictoryCondition extends VictoryCondition {
    private int star;
    private int minCount;
    public CardOfStarVictoryCondition(int star, int minCount) {
        this.star = star;
        this.minCount = minCount;
    }
    public boolean meetCriteria(GameResult result) {
        int count = 0;
        for (CardInfo card : result.getWinner().getAllCards()) {
            if (card.getStar() == this.star) {
                ++count;
            }
        }
        return count >= minCount;
    }
    @Override
    public String getDescription() {
        return "卡组中" + star + "星卡牌不小于" + minCount + "张";
    }
}

class CardOfRaceVictoryCondition extends VictoryCondition {
    private Race race;
    private int minCount;
    public CardOfRaceVictoryCondition(Race race, int minCount) {
        this.race = race;
        this.minCount = minCount;
    }
    public boolean meetCriteria(GameResult result) {
        int count = 0;
        for (CardInfo card : result.getWinner().getAllCards()) {
            if (card.getRace() == this.race) {
                ++count;
            }
        }
        return count >= minCount;
    }
    @Override
    public String getDescription() {
        return "卡组中" + race.getDisplayName() + "种族卡牌不小于" + minCount + "张";
    }
}

class NoRuneVictoryCondition extends VictoryCondition {
    private RuneClass runeClass;
    public NoRuneVictoryCondition(RuneClass runeClass) {
        this.runeClass = runeClass;
    }
    public boolean meetCriteria(GameResult result) {
        List<RuneInfo> runes = result.getWinner().getRuneBox().getRunes();
        if (runeClass == null) {
            return runes.size() == 0;
        }
        for (RuneInfo rune : result.getWinner().getRuneBox().getRunes()) {
            if (rune.getRuneClass() == runeClass) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String getDescription() {
        return "卡组中无符文";
    }
}

class HasRuneVictoryCondition extends VictoryCondition {
    private RuneClass runeClass;
    public HasRuneVictoryCondition(RuneClass runeClass) {
        if (runeClass == null) {
            throw new IllegalArgumentException("runeClass should not be null");
        }
        this.runeClass = runeClass;
    }
    public boolean meetCriteria(GameResult result) {
        for (RuneInfo rune : result.getWinner().getRuneBox().getRunes()) {
            if (rune.getRuneClass() == runeClass) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String getDescription() {
        return "卡组中包含" + runeClass.getDisplayName() + "属性符文";
    }
}