package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

/**
 * Block 20 * level damages
 * Overlappable.
 * 
 * Only effective to normal attack.
 * @author °×°×
 *
 */
public final class BlockFeature {
    public static int apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int block = cardFeature.getImpact();
        int actualDamage = originalDamage - block;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardFeature);
        ui.blockDamage(attacker, defender, cardFeature, originalDamage, actualDamage);
        return actualDamage;
    }
}
