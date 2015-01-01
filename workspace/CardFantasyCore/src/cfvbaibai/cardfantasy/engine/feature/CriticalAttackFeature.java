package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class CriticalAttackFeature extends PreAttackCardFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, CardInfo defender) {
        Skill skill = featureInfo.getFeature();
        int adjAT = skill.getImpact() * attacker.getLevel1AT() / 100;
        boolean bingo = resolver.getStage().getRandomizer().roll100(50);
        resolver.getStage().getUI().useSkill(attacker, skill, bingo);
        if (bingo) {
            resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
            attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
        }
    }
}
