package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class IceArmorFeature {
    public static int apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature);
        if (resolver.resolveCounterBlockFeature(feature, attacker, defender)) {
            return originalDamage;
        }
        
        int maxDamage = feature.getImpact();
        int actualDamage = originalDamage;
        if (actualDamage > maxDamage) {
            actualDamage = maxDamage;
        }

        ui.blockDamage(attacker, defender, feature, originalDamage, actualDamage);
        return actualDamage;
    }
}
