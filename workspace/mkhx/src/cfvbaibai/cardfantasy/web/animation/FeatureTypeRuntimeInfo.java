package cfvbaibai.cardfantasy.web.animation;

import cfvbaibai.cardfantasy.data.FeatureType;

public class FeatureTypeRuntimeInfo {

    public FeatureTypeRuntimeInfo(FeatureType featureType) {
        this.name = featureType.name();
        this.growable = featureType.isGrowable();
        this.wikiId = featureType.getWikiId();
    }

    private String name;
    private boolean growable;
    private String wikiId;
    
    public String getWikiId() {
        return wikiId;
    }
    
    public String getName() {
        return name;
    }

    public boolean isGrowable() {
        return growable;
    }
}
