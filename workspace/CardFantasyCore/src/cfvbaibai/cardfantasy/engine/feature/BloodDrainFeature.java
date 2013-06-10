package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class BloodDrainFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int damage) {
        if (attacker == null || attacker.isDead()) {
            throw new CardFantasyRuntimeException("attacker is null or dead!");
        }
        int drainedHP = feature.getImpact() * damage / 100;
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, feature);
        ui.healCard(attacker, attacker, feature, drainedHP);
        resolver.applyDamage(attacker, -drainedHP);
    }
}
