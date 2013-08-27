package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class HolyFireFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        if (defender.getGrave().size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        CardInfo victim = defender.getGrave().pickRandom(1, true).get(0);
        ui.useSkill(attacker, victim, cardFeature, true);
        ui.cardToOutField(defender, victim);
        defender.getGrave().removeCard(victim);
        defender.getOutField().addCard(victim);
    }
}
