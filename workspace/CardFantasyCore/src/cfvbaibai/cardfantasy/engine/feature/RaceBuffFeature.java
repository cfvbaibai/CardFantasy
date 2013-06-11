package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;

public final class RaceBuffFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo card, Race race,
            FeatureEffectType effectType) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Feature feature = featureInfo.getFeature();
        int impact = feature.getImpact();
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            // race == null => all races 本源之力/本源守护
            if (ally == card || race != null && ally.getRace() != race) {
                continue;
            }
            if (ally.getEffectsCausedBy(featureInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, feature);
                if (effectType == FeatureEffectType.ATTACK_CHANGE) {
                    resolver.getStage().getUI().adjustAT(card, ally, impact, feature);
                } else if (effectType == FeatureEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().adjustHP(card, ally, impact, feature);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                }
                ally.addEffect(new FeatureEffect(effectType, featureInfo, impact, false));
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
            // race == null => all races 本源之力/本源守护
            if (ally == card || race != null && ally.getRace() != race) {
                continue;
            }
            for (FeatureEffect effect : ally.getEffectsCausedBy(feature)) {
                if (effect.getType() == FeatureEffectType.ATTACK_CHANGE) {
                    resolver.getStage().getUI().loseAdjustATEffect(ally, effect);
                } else if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().loseAdjustHPEffect(ally, effect);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effect.getType().name());
                }
                ally.removeEffect(effect);
            }
        }
    }
}
