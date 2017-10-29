package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class TheSword {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) {
        if (attacker == null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        int adjAT = skill.getImpact();
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
    }
}
