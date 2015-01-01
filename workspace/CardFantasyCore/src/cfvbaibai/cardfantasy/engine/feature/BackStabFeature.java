package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class BackStabFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker) {
        Skill skill = featureInfo.getFeature();
        int adjAT = skill.getImpact();
        if (attacker.hasUsed(featureInfo)) {
            return;
        }

        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
        attacker.setUsed(featureInfo);
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(feature);
        for (SkillEffect effect : effects) {
            if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                resolver.getStage().getUI().loseAdjustATEffect(card, effect);
                card.removeEffect(effect);
            }
        }
    }
}
