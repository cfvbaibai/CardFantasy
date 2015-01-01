package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class RevengeFeature extends PreAttackCardFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker) {
        int myDeadCount = attacker.getOwner().getGrave().size();
        if (myDeadCount == 0) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        int adjAT = skill.getImpact() * myDeadCount;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
    }
}
