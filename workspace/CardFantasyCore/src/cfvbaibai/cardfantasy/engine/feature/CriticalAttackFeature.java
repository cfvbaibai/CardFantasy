package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class CriticalAttackFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker, CardInfo defender) {
        int adjAT = feature.getImpact() * attacker.getOriginalAT() / 100;
        if (Randomizer.roll100() < 50) {
            resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, feature);
            attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, adjAT));
        }
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<FeatureEffect> effects = card.getEffectsCausedBy(feature);
        for (FeatureEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustAttackEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
