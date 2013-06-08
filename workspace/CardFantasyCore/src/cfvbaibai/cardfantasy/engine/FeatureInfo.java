package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;

public class FeatureInfo extends Feature {

    private CardInfo owner;
    public CardInfo getOwner() {
        return owner;
    }
    public FeatureInfo(CardInfo owner, FeatureType type, int level, int unlockLevel) {
        super(type, level, unlockLevel);
        this.owner = owner;
    }
    public FeatureInfo(CardInfo owner, Feature feature) {
        this(owner, feature.getType(), feature.getLevel(), feature.getUnlockLevel());
    }

}
