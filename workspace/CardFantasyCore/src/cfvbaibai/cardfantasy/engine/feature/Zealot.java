package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Zealot {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, CardInfo defender,
            OnAttackBlockingResult result) {
        if (result.getDamage() == 0 || defender == null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact();
        resolver.getStage().getUI().useSkill(defender, defender, skill, true);
        resolver.getStage().getUI().adjustAT(defender, defender, adjAT, skill);
        defender.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
    }
}
