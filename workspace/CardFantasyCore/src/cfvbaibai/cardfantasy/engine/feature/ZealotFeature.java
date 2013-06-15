package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class ZealotFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, EntityInfo attacker, CardInfo defender,
            OnAttackBlockingResult result) {
        if (result.getDamage() == 0 || defender == null) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        int adjAT = feature.getImpact();
        resolver.getStage().getUI().useSkill(defender, defender, feature);
        resolver.getStage().getUI().adjustAT(defender, defender, adjAT, feature);
        defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, true));
    }
}
