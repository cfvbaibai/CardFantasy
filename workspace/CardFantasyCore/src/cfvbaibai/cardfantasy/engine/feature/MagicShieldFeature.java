package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class MagicShieldFeature {
    public static int apply(FeatureResolver resolver, Feature feature, CardInfo attacker, CardInfo defender,
            int originalDamage) {
        int maxDamage = feature.getImpact();
        int actualDamage = Math.min(maxDamage, originalDamage);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature);
        ui.blockDamage(attacker, defender, feature, originalDamage, actualDamage);
        return actualDamage;
    }
}
