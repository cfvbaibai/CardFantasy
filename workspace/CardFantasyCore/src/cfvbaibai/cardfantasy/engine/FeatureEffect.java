package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.FeatureType;

public class FeatureEffect {
    private FeatureEffectType type;
    private FeatureType cause;
    private int value;
    public FeatureEffectType getType() {
        return type;
    }
    public void setType(FeatureEffectType type) {
        this.type = type;
    }
    public FeatureType getCause() {
        return cause;
    }
    public void setCause(FeatureType cause) {
        this.cause = cause;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public FeatureEffect(FeatureEffectType type, FeatureType cause, int value) {
        super();
        this.type = type;
        this.cause = cause;
        this.value = value;
    }
}
