package cfvbaibai.cardfantasy.engine;

public class CardStatusItem {
    private CardStatusType type;
    private int effect;
    private FeatureInfo cause;

    private CardStatusItem(CardStatusType type, int effect, FeatureInfo cause) {
        this.type = type;
        this.effect = effect;
        this.cause = cause;
    }

    public FeatureInfo getCause() {
        return this.cause;
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

    public static CardStatusItem paralyzed(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.Âé±Ô, 0, cause);
    }

    public static CardStatusItem frozen(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.±ù¶³, 0, cause);
    }

    public static CardStatusItem poisoned(int effect, FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.ÖÐ¶¾, effect, cause);
    }

    public static CardStatusItem trapped(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.Ëø¶¨, 0, cause);
    }

    public static CardStatusItem burning(int effect, FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.È¼ÉÕ, effect, cause);
    }

    public String getShortDesc() {
        StringBuffer sb = new StringBuffer();
        sb.append(getType().name());
        if (getType().isQuantitive()) {
            sb.append("(");
            sb.append(getEffect());
            sb.append(")");
        }
        sb.append(":");
        sb.append(cause.getType().name());
        sb.append(cause.getLevel());
        sb.append(":");
        sb.append(cause.getOwner().getShortDesc(false));
        return sb.toString();
    }
}
