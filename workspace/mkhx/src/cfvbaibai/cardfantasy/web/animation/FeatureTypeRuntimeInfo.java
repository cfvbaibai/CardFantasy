package cfvbaibai.cardfantasy.web.animation;

import cfvbaibai.cardfantasy.data.FeatureType;

public class FeatureTypeRuntimeInfo {

    public FeatureTypeRuntimeInfo(FeatureType featureType) {
        this.name = featureType.name();
        this.growable = featureType.isGrowable();
    }

    private String name;
    private boolean growable;
    
    public String getName() {
        return name;
    }
    public boolean isGrowable() {
        return growable;
    }
}
