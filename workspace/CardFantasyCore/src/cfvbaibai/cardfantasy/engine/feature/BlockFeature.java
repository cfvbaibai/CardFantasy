package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

/**
 * Block 20 * level damages
 * Overlappable.
 * 
 * Only effective to normal attack.
 * @author °×°×
 *
 */
public final class BlockFeature {
    public static int apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int block = feature.getImpact();
        int actualDamage = originalDamage - block;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature);
        ui.blockDamage(attacker, defender, feature, originalDamage, actualDamage);
        return actualDamage;
    }
}
