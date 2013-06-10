package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class OverdrawFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker) {
        int adjAT = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, feature);
        ui.adjustAT(attacker, attacker, adjAT, feature);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, adjAT, true));
        ui.attackCard(attacker, attacker, feature, adjAT);
        attacker.applyDamage(adjAT);
    }
}
