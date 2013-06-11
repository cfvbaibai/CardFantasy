package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class DodgeFeature {
    public static boolean apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int dodgeRate = cardFeature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardFeature);
        if (resolver.getStage().getRandomizer().roll100(dodgeRate)) {
            if (resolver.resolveCounterBlockFeature(cardFeature, attacker, defender)) {
                return false;
            }
            ui.blockDamage(attacker, defender, cardFeature, originalDamage, 0);
            return true;
        } else {
            return false;
        }
    }
}
