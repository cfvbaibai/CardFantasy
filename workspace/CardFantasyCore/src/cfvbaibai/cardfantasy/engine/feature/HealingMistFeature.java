package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;

public class HealingMistFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo healer) {
        if (healer == null) {
            return;
        }

        Field field = healer.getOwner().getField();
        List<CardInfo> healeeCandidates = resolver.getAdjacentCards(field, healer.getPosition());
        RainfallFeature.healCards(resolver, healer, cardFeature, healeeCandidates);
    }
}
