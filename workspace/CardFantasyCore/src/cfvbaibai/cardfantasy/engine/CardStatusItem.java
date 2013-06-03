package cfvbaibai.cardfantasy.engine;

public class CardStatusItem {
    private CardStatusType type;
    private int effect;
    
    CardStatusItem(CardStatusType type, int effect) {
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
    
    public static CardStatusItem paralyzed() {
        return new CardStatusItem(CardStatusType.PARALYZED, 0);
    }

    public static CardStatusItem frozen() {
        return new CardStatusItem(CardStatusType.FROZEN, 0);
    }

    public static CardStatusItem poisoned(int effect) {
        return new CardStatusItem(CardStatusType.POISONED, effect);
    }

    public static CardStatusItem trapped() {
        return new CardStatusItem(CardStatusType.TRAPPED, 0);
    }

    public String getShortDesc() {
        StringBuffer sb = new StringBuffer();
        sb.append(getType().name());
        if (getType().isQuantitive()) {
            sb.append("(");
            sb.append(getEffect());
            sb.append(")");
        }
        return sb.toString();
    }
}
