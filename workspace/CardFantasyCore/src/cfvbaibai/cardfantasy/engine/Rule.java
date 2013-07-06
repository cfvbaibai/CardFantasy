package cfvbaibai.cardfantasy.engine;

public class Rule {
    private int maxHandCards;
    private int maxRound;
    private int firstPlayer;

    public Rule(int maxHandCards, int maxRound, int firstPlayer) {
        if (firstPlayer < 0) {
            throw new IllegalArgumentException("Invalid firstPlayer: " + firstPlayer);
        }
        this.maxHandCards = maxHandCards;
        this.maxRound = maxRound;
        this.firstPlayer = firstPlayer;
    }

    public int getMaxHandCards() {
        return this.maxHandCards;
    }
    
    public int getMaxRound() {
        return this.maxRound;
    }
    
    public int getFirstPlayer() {
        return this.firstPlayer;
    }

    public static Rule getDefault() {
        return new Rule(5, 100, 0);
    }
}