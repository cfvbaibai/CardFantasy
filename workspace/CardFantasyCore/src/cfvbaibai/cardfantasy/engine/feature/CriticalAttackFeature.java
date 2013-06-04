package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class CriticalAttackFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) {
        int adjAT = feature.getImpact() * attacker.getOriginalAT() / 100;
        if (Randomizer.roll100() < 50) {
            resolver.getStage().getUI().adjustAT(attacker, adjAT, feature);
            attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature.getType(), adjAT));
        }
    }

    public static void remove(FeatureResolver resolver, FeatureEffect effect, CardInfo card) {
        resolver.getStage().getUI().recoverAT(card, effect.getCause(), effect.getValue());
        card.removeEffect(effect);
    }
}
