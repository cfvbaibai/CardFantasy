package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;


public class SkillEffect {
    private SkillEffectType type;
    @NonSerializable
    private FeatureInfo cause;
    private int value;
    private boolean eternal;
    
    public SkillEffectType getType() {
        return type;
    }
    public void setType(SkillEffectType type) {
        this.type = type;
    }
    public FeatureInfo getCause() {
        return cause;
    }
    public void setCause(FeatureInfo cause) {
        this.cause = cause;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public EntityInfo getSource() {
        return this.cause.getOwner();
    }
    public boolean isEternal() {
        return this.eternal;
    }
    public SkillEffect(SkillEffectType type, FeatureInfo cause, int value, boolean eternal) {
        this.type = type;
        this.cause = cause;
        this.value = value;
        this.eternal = eternal;
    }
}
