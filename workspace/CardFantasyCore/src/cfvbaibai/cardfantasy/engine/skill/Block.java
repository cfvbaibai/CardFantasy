package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

/**
 * Block 20 * level damages Overlappable.
 * 
 * Only effective to normal attack.
 */
public final class Block {
    public static int apply(Skill skill, SkillResolver resolver, EntityInfo attacker, EntityInfo victim,
            EntityInfo blocker, int originalDamage) {
        int block = skill.getImpact();
        int actualDamage = originalDamage - block;
        CardInfo attack = (CardInfo) attacker;
        CardInfo blockOne = (CardInfo) blocker;
        if(resolver.resolveStopBlockSkill(skill, attack, blockOne))
        {
            actualDamage = originalDamage;
        }
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.protect(blocker, attacker, victim, null, skill);
        ui.blockDamage(blocker, attacker, victim, skill, originalDamage, actualDamage);
        return actualDamage;
    }
}
