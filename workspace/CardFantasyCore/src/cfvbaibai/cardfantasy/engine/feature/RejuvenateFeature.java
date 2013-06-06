package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class RejuvenateFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo card) {
        int healHP = feature.getImpact();
        if (healHP + card.getHP() > card.getCard().getMaxHP()) {
            healHP = card.getCard().getMaxHP() - card.getHP();
        }
        if (healHP == 0) {
            return;
        }
        resolver.getStage().getUI().healCard(card, card, feature, healHP);
        resolver.applyDamage(card, -healHP);
    }
}
