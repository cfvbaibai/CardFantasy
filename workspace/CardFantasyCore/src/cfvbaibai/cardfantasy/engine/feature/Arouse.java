package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class Arouse extends PreAttackCardSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) {
        int myAliveCardCount = attacker.getOwner().getField().getAliveCards().size();
        if (myAliveCardCount == 0) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact() * myAliveCardCount;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
