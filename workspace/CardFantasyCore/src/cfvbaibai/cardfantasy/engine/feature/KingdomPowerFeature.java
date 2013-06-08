package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;

public final class KingdomPowerFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (feature.getType() != FeatureType.王国之力) {
            throw new CardFantasyRuntimeException("feature.getType() is not 王国之力!");
        }
        int adjAT = feature.getImpact();
        Field field = card.getOwner().getField();
        for (CardInfo ally : field) {
            if (ally == null || ally == card || ally.getRace() != Race.王国) {
                continue;
            }
            if (ally.getEffectsCausedBy(feature).isEmpty()) {
                resolver.getStage().getUI().adjustAT(card, ally, adjAT, feature);
                ally.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, adjAT));
            }
        }
    }
    
    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (feature == null) {
            throw new CardFantasyRuntimeException("feature cannot be null");
        }
        Field field = card.getOwner().getField();
        for (CardInfo ally : field) {
            if (ally == null || ally == card || ally.getRace() != Race.王国) {
                continue;
            }
            for (FeatureEffect effect : ally.getEffectsCausedBy(feature)) {
                resolver.getStage().getUI().loseAdjustAttackEffect(ally, effect);
                ally.removeEffect(effect);
            }
        }
    }
}
