package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatus;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class PursuitFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, CardInfo defender) {
        CardStatus status = defender.getStatus();
        Feature feature = featureInfo.getFeature();
        if (status.containsStatus(CardStatusType.中毒) || status.containsStatus(CardStatusType.冰冻) ||
                status.containsStatus(CardStatusType.燃烧) || status.containsStatus(CardStatusType.麻痹)) {
            int adjAT = (int) (attacker.getLevel1AT() * feature.getImpact() / 100);
            resolver.getStage().getUI().useSkill(attacker, feature, true);
            resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, feature);
            attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
        }
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<FeatureEffect> effects = card.getEffectsCausedBy(feature);
        for (FeatureEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
