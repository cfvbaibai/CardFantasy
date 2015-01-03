package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class Revenge extends PreAttackCardSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) {
        int myDeadCount = attacker.getOwner().getGrave().size();
        if (myDeadCount == 0) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact() * myDeadCount;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
