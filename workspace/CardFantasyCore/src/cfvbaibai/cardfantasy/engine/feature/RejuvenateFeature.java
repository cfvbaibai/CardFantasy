package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class RejuvenateFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo card) {
        int healHP = cardFeature.getImpact();
        if (healHP + card.getHP() > card.getMaxHP()) {
            healHP = card.getMaxHP() - card.getHP();
        }
        if (healHP == 0) {
            return;
        }
        OnAttackBlockingResult result = resolver.resolveHealBlockingFeature(card, card, cardFeature);
        if (!result.isAttackable()) {
            return;
        }
        resolver.getStage().getUI().useSkill(card, cardFeature, true);
        resolver.getStage().getUI().healCard(card, card, cardFeature, healHP);
        resolver.applyDamage(card, -healHP);
    }
}
