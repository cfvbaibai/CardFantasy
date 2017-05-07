package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class HolyShield {
    public static boolean apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, CardInfo victim) {
        if (victim == null) {
            return true;
        }
        if (victim.hasUsed(skillUseInfo)) {
            return true;
        }
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(victim, skill, true);
        victim.setUsed(skillUseInfo);
        return false;
    }
    public static void resetApply(SkillUseInfo skillUseInfo, SkillResolver resolver,  CardInfo victim){
        if (victim == null) {
            return ;
        }
        if (!victim.hasUsed(skillUseInfo)) {
            return ;
        }
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(victim, skill, true);
        List<SkillEffect> effects = victim.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : effects) {
            if(effect.getType() == SkillEffectType.SKILL_USED)
            victim.removeEffect(effect);
        }
    }
}
