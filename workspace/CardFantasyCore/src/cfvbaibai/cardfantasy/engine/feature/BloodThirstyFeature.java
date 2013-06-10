package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class BloodThirstyFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker, int normalAttackDamage) {
        if (attacker == null || normalAttackDamage <= 0) {
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, feature);
        int adjAT = feature.getImpact();
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, feature);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, adjAT, true));
    }
}
