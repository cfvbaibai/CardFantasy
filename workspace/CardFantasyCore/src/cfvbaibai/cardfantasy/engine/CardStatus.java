package cfvbaibai.cardfantasy.engine;

public class CardStatus {
    private CardStatusType type;
    private int effect;
    
    CardStatus(CardStatusType type, int effect) {
        this.type = type;
        this.effect = effect;
    }

    public CardStatusType getType() {
        return type;
    }

    public void setType(CardStatusType type) {
        this.type = type;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public static CardStatus normal() {
        return new CardStatus(CardStatusType.NORMAL, 0);
    }
    
    public static CardStatus paralyzed() {
        return new CardStatus(CardStatusType.PARALYZED, 0);
    }

    public static CardStatus frozen() {
        return new CardStatus(CardStatusType.FROZEN, 0);
    }

    public static CardStatus poisoned(int effect) {
        return new CardStatus(CardStatusType.POISONED, effect);
    }

    public static CardStatus trapped() {
        return new CardStatus(CardStatusType.TRAPPED, 0);
    }
}
