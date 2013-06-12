package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class MagicShieldFeature {
    public static int apply(FeatureResolver resolver, Feature cardFeature, EntityInfo attacker, CardInfo defender,
            int originalDamage) {
        int maxDamage = cardFeature.getImpact();
        int actualDamage = Math.min(maxDamage, originalDamage);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardFeature);
        ui.blockDamage(defender, attacker, defender, cardFeature, originalDamage, actualDamage);
        return actualDamage;
    }
}
