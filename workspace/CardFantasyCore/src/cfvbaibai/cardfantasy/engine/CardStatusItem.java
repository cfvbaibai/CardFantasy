package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;

public class CardStatusItem {
    private CardStatusType type;
    private int effect;
    @NonSerializable
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
        return new CardStatusItem(CardStatusType.麻痹, 0, cause);
    }

    public static CardStatusItem frozen(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.冰冻, 0, cause);
    }

    public static CardStatusItem poisoned(int effect, FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.中毒, effect, cause);
    }

    public static CardStatusItem trapped(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.锁定, 0, cause);
    }

    public static CardStatusItem burning(int effect, FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.燃烧, effect, cause);
    }
    
    public static CardStatusItem wound(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.裂伤, 0, cause);
    }

    public static CardStatusItem weak(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.复活, 0, cause);
    }
    
    public static CardStatusItem confused(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.迷惑, 0, cause);
    }
    
    public static CardStatusItem softened(FeatureInfo cause) {
        return new CardStatusItem(CardStatusType.弱化, 0, cause);
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