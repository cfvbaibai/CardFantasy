package cfvbaibai.cardfantasy.engine;

public class Rule {
    private int maxHandCards;
    private int maxRound;
    private int firstPlayer;
    private boolean bossBattle;

    public Rule(int maxHandCards, int maxRound, int firstPlayer, boolean bossBattle) {
        if (firstPlayer < -1) {
            throw new IllegalArgumentException("Invalid firstPlayer: " + firstPlayer);
        }
        this.maxHandCards = maxHandCards;
        this.maxRound = maxRound;
        this.firstPlayer = firstPlayer;
        this.bossBattle = bossBattle;
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
    
    public boolean isBossBattle() {
        return this.bossBattle;
    }

    public static Rule getDefault() {
        return new Rule(5, 999, 0, false);
    }
    
    public static Rule getBossBattle() {
        return new Rule(5, 999, 0, true);
    }
}