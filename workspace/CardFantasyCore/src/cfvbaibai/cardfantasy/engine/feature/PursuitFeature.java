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
        if (status.containsStatus(CardStatusType.ÖÐ¶¾) || status.containsStatus(CardStatusType.±ù¶³) ||
                status.containsStatus(CardStatusType.È¼ÉÕ) || status.containsStatus(CardStatusType.Âé±Ô)) {
            int adjAT = (int) (attacker.getOriginalAT() * feature.getImpact() / 100);
            resolver.getStage().getUI().useSkill(attacker, feature);
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
