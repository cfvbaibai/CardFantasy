package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

/**
 * Block 20 * level damages Overlappable.
 * 
 * Only effective to normal attack.
 * 
 * @author °×°×
 * 
 */
public final class BlockFeature {
    public static int apply(Feature feature, FeatureResolver resolver, EntityInfo attacker, EntityInfo victim,
            EntityInfo blocker, int originalDamage) {
        int block = feature.getImpact();
        int actualDamage = originalDamage - block;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.protect(blocker, attacker, victim, null, feature);
        ui.blockDamage(blocker, attacker, victim, feature, originalDamage, actualDamage);
        return actualDamage;
    }
}
