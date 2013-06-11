package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;

public class FeatureInfo {

    private EntityInfo owner;
    private Feature feature;

    public EntityInfo getOwner() {
        return owner;
    }
    
    public Feature getFeature() {
        return feature;
    }
    
    public FeatureType getType() {
        return this.feature.getType();
    }

    public FeatureInfo(EntityInfo owner, Feature feature) {
        this.feature = feature;
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
