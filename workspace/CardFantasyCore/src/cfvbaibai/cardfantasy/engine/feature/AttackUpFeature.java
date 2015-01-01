package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class AttackUpFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, EntityInfo caster, int targetCount) {
        Skill skill = featureInfo.getFeature();
        int adjAT = skill.getImpact();
        List<CardInfo> targets = resolver.getStage().getRandomizer().pickRandom(
            caster.getOwner().getField().toList(), targetCount, true, null);
        resolver.getStage().getUI().useSkill(caster, targets, skill, true);
        for (CardInfo target : targets) {
            resolver.getStage().getUI().adjustAT(caster, target, adjAT, skill);
            target.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
        }
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(feature);
        for (SkillEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
