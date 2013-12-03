package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class CounterBiteFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo card) throws HeroDieSignal {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card is null or dead!");
        }
        if (card.hasUsed(featureInfo)) {
            return;
        }
        Feature cardFeature = featureInfo.getFeature();
        int damage = cardFeature.getImpact();
        resolver.attackHero(card, card.getOwner(), cardFeature, damage);
        card.setUsed(featureInfo);
    }
}