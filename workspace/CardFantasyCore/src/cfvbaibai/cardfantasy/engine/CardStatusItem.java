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
        return new CardStatusItem(CardStatusType.¬È±‘, 0, cause);
    }

    public static CardStatusItem frozen(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.±˘∂≥, 0, cause);
    }

    public static CardStatusItem poisoned(int effect, FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.÷–∂æ, effect, cause);
    }

    public static CardStatusItem trapped(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.À¯∂®, 0, cause);
    }

    public static CardStatusItem burning(int effect, FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.»º…’, effect, cause);
    }
    
    public static CardStatusItem wound(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.¡—…À, 0, cause);
    }

    public static CardStatusItem weak(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.–È»ı, 0, cause);
    }
    
    public static CardStatusItem confused(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.√‘ªÛ, 0, cause);
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
        sb.append(cause.getFeature().getType().name());
        sb.append(cause.getFeature().getLevel());
        sb.append(":");
        sb.append(cause.getOwner().getShortDesc());
        return sb.toString();
    }
}
