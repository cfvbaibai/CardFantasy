package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class MagicShieldFeature {
    public static int apply(FeatureResolver resolver, Feature feature, EntityInfo attacker, CardInfo defender,
            Feature attackFeature, int originalDamage) {
        if (!attackFeature.getType().containsTag(FeatureTag.魔法)) {
            return originalDamage;
        }
        int maxDamage = feature.getImpact();
        int actualDamage = Math.min(maxDamage, originalDamage);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature, true);
        ui.blockDamage(defender, attacker, defender, feature, originalDamage, actualDamage);
        return actualDamage;
    }
}
