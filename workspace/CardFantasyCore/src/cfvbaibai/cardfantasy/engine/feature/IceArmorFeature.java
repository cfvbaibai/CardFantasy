package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class IceArmorFeature {
    public static int apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardFeature, true);
        if (resolver.resolveCounterBlockFeature(cardFeature, attacker, defender)) {
            return originalDamage;
        }
        
        int maxDamage = cardFeature.getImpact();
        int actualDamage = originalDamage;
        if (actualDamage > maxDamage) {
            actualDamage = maxDamage;
        }

        ui.blockDamage(defender, attacker, defender, cardFeature, originalDamage, actualDamage);
        return actualDamage;
    }
}
