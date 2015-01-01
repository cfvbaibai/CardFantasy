package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class RevengeFeature extends PreAttackCardFeature {
    public static void apply(FeatureResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) {
        int myDeadCount = attacker.getOwner().getGrave().size();
        if (myDeadCount == 0) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        int adjAT = skill.getImpact() * myDeadCount;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
