package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.FeatureType;

public class FeatureInfo {

    @NonSerializable
    private EntityInfo owner;
    private Skill skill;

    public EntityInfo getOwner() {
        return owner;
    }
    
    public Skill getFeature() {
        return skill;
    }
    
    public FeatureType getType() {
        return this.skill.getType();
    }

    public FeatureInfo(EntityInfo owner, Skill skill) {
        this.skill = skill;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof FeatureInfo) {
            FeatureInfo other = (FeatureInfo)obj;
            return getOwner() == other.getOwner() && getFeature().equals(other.getFeature());
        }
        return false;
    }
}
