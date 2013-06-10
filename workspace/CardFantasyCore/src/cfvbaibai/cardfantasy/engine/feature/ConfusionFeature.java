package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.Player;

public final class ConfusionFeature {
    public static void apply(FeatureInfo feature, FeatureResolver resolver, CardInfo attacker, Player defender,
            int victimCount) {
        int rate = feature.getImpact();
        List<CardInfo> victims = defender.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature).isAttackable()) {
                continue;
            }
            if (resolver.getStage().getRandomizer().roll100(rate)) {
                CardStatusItem status = CardStatusItem.confused(feature);
                ui.addCardStatus(attacker, victim, feature, status);
                victim.addStatus(status);
            }
        }
    }
}
