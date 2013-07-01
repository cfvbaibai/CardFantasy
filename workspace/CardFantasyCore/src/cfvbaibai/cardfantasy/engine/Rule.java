package cfvbaibai.cardfantasy.engine;

public class Rule {
    private int maxHandCards;
    private int maxRound;

    public Rule(int maxHandCards, int maxRound) {
        this.maxHandCards = maxHandCards;
        this.maxRound = maxRound;
    }

    public int getMaxHandCards() {
        return this.maxHandCards;
    }
    
    public int getMaxRound() {
        return this.maxRound;
    }

    public static Rule getDefault() {
        return new Rule(6, 50);
    }
}