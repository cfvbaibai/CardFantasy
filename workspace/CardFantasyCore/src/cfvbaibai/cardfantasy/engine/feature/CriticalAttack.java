package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class CriticalAttack extends PreAttackCardFeature {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender) {
        Skill skill = skillUseInfo.getFeature();
        int adjAT = skill.getImpact() * attacker.getLevel1AT() / 100;
        boolean bingo = resolver.getStage().getRandomizer().roll100(50);
        resolver.getStage().getUI().useSkill(attacker, skill, bingo);
        if (bingo) {
            resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
            attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
        }
    }
}
