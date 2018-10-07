package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class MagicShield {
    public static int apply(SkillResolver resolver, Skill skill, EntityInfo attacker, CardInfo defender,
            Skill attackSkill, int originalDamage) {
        if (!resolver.isMagicalSkill(attackSkill)) {
            return originalDamage;
        }
        int maxDamage = skill.getImpact();
        if(skill.getType() == SkillType.铁骨衣)
        {
            maxDamage = 140 - skill.getImpact()*10;
        }
        int actualDamage = Math.min(maxDamage, originalDamage);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, skill, true);
        ui.blockDamage(defender, attacker, defender, skill, originalDamage, actualDamage);
        return actualDamage;
    }
}
