package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class EnergyDrainFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, CardInfo defender,
            OnAttackBlockingResult result) {
        if (result.getDamage() == 0 || defender == null) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        int adjAT = -attacker.getLevel1AT() * feature.getImpact() / 100;
        resolver.getStage().getUI().useSkill(defender, attacker, feature, true);
        resolver.getStage().getUI().adjustAT(defender, attacker, adjAT, feature);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, true));
        if (!defender.isDead()) {
            resolver.getStage().getUI().adjustAT(defender, defender, -adjAT, feature);
            defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, -adjAT, true));
        }
    }
}
