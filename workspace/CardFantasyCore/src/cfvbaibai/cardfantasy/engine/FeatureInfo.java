package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Feature;

public class FeatureInfo extends Feature {

    private CardInfo owner;

    public CardInfo getOwner() {
        return owner;
    }

    public FeatureInfo(CardInfo owner, Feature feature) {
        super(feature.getType(), feature.getLevel(), feature.getUnlockLevel(), feature.isSummonFeature(), feature
                .isDeathFeature());
        this.owner = owner;
    }

    public static boolean equals(FeatureInfo a, FeatureInfo b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.getOwner() == b.getOwner() &&
                a.getType() == b.getType() &&
                a.getLevel() == b.getLevel() &&
                a.isDeathFeature() == b.isDeathFeature() &&
                a.isSummonFeature() == b.isSummonFeature();
    }

}
