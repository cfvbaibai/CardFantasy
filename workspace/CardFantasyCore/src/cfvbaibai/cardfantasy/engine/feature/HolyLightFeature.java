package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class HolyLightFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) {
        if (defender.getRace() == Race.Hell) {
            double rate = 0.15 + ((double)feature.getLevel()) * 0.15;
            int adjAT = (int) (attacker.getOriginalAT() * rate);
            resolver.getStage().getUI().adjustAT(attacker, adjAT, feature);
            attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature.getType(), adjAT));
        }
    }
}
