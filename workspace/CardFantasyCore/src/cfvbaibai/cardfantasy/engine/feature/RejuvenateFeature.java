package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class RejuvenateFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo card) {
        int healHP = feature.getImpact();
        if (healHP + card.getHP() > card.getMaxHP()) {
            healHP = card.getMaxHP() - card.getHP();
        }
        if (healHP == 0) {
            return;
        }
        OnAttackBlockingResult result = resolver.resolveHealBlockingFeature(card, card, feature);
        if (!result.isAttackable()) {
            return;
        }
        resolver.getStage().getUI().healCard(card, card, feature, healHP);
        resolver.applyDamage(card, -healHP);
    }
}
