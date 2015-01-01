package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;

public final class RaceBuffFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo card, Race race,
            SkillEffectType effectType) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = featureInfo.getFeature();
        int impact = skill.getImpact();
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            if (ally == card || race != null && ally.getRace() != race) {
                continue;
            }
            if (ally.getEffectsCausedBy(featureInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, skill, true);
                if (effectType == SkillEffectType.ATTACK_CHANGE) {
                    resolver.getStage().getUI().adjustAT(card, ally, impact, skill);
                } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().adjustHP(card, ally, impact, skill);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                }
                ally.addEffect(new SkillEffect(effectType, featureInfo, impact, false));
            }
        }
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card, Race race) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (feature == null) {
            throw new CardFantasyRuntimeException("feature cannot be null");
        }
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            if (ally == card || race != null && ally.getRace() != race) {
                continue;
            }
            for (SkillEffect effect : ally.getEffectsCausedBy(feature)) {
                if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                    resolver.getStage().getUI().loseAdjustATEffect(ally, effect);
                } else if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().loseAdjustHPEffect(ally, effect);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effect.getType().name());
                }
                ally.removeEffect(effect);
            }
        }
    }
}
