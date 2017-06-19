package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class MagicArmor {
    public static int apply(SkillResolver resolver, Skill skill, EntityInfo attacker, CardInfo defender,
                            Skill attackSkill, int originalDamage) {
        if (!resolver.isMagicalSkill(attackSkill)) {
            return originalDamage;
        }
        int reduceDamage = skill.getImpact();
        int actualDamage = originalDamage*(100-reduceDamage)/100;
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, skill, true);
        ui.blockDamage(defender, attacker, defender, skill, originalDamage, actualDamage);
        return actualDamage;
    }
}