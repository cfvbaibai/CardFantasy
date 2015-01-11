package cfvbaibai.cardfantasy.engine;

public class Rule {
    private int maxHandCards;
    private int maxRound;
    // -1: By rule, 0: PlayerA, 1: PlayerB
    private int firstPlayer;
    // 0: Random, 1: Ordered
    private int deckOrder;
    private boolean bossBattle;

    public Rule(int maxHandCards, int maxRound, int firstPlayer, int deckOrder, boolean bossBattle) {
        if (firstPlayer < -1 || firstPlayer > 1) {
            throw new IllegalArgumentException("Invalid firstPlayer: " + firstPlayer);
        }
        if (deckOrder != 0 && deckOrder != 1) {
            throw new IllegalArgumentException("Invalid deckOrder: " + deckOrder);
        }
        this.maxHandCards = maxHandCards;
        this.maxRound = maxRound;
        this.firstPlayer = firstPlayer;
        this.deckOrder = deckOrder;
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
    
    public int getDeckOrder() {
        return this.deckOrder;
    }
    
    public boolean isBossBattle() {
        return this.bossBattle;
    }

    public static Rule getDefault() {
        return new Rule(5, 999, 0, 0, false);
    }
    
    public static Rule getBossBattle() {
        return new Rule(5, 999, 0, 0, true);
    }
}