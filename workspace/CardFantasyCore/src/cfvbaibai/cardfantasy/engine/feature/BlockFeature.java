package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

/**
 * Block 20 * level damages Overlappable.
 * 
 * Only effective to normal attack.
 */
public final class BlockFeature {
    public static int apply(Skill skill, FeatureResolver resolver, EntityInfo attacker, EntityInfo victim,
            EntityInfo blocker, int originalDamage) {
        int block = skill.getImpact();
        int actualDamage = originalDamage - block;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.protect(blocker, attacker, victim, null, skill);
        ui.blockDamage(blocker, attacker, victim, skill, originalDamage, actualDamage);
        return actualDamage;
    }
}
