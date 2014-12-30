package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class ArouseFeature extends PreAttackCardFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker) {
        int myDeadCount = attacker.getOwner().getField().size();
        if (myDeadCount == 0) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        int adjAT = feature.getImpact() * myDeadCount;
        resolver.getStage().getUI().useSkill(attacker, feature, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, feature);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
    }
}
