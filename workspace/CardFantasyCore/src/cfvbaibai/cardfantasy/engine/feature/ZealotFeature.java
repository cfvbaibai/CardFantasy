package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class ZealotFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int damage) {
        if (damage == 0 || defender == null) {
            return;
        }
        int adjAT = feature.getImpact();
        resolver.getStage().getUI().adjustAT(defender, adjAT, feature);
        defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature.getType(), adjAT));
    }
}
