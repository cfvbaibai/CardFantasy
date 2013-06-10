package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class DodgeFeature {
    public static boolean apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int dodgeRate = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature);
        if (resolver.getStage().getRandomizer().roll100(dodgeRate)) {
            if (resolver.resolveCounterBlockFeature(feature, attacker, defender)) {
                return false;
            }
            ui.blockDamage(attacker, defender, feature, originalDamage, 0);
            return true;
        } else {
            return false;
        }
    }
}
