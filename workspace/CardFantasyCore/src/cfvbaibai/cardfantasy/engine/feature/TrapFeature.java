package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Trap 1*level enemy card at 65% probability.
 * 
 * Can be blocked by Immue
 * @author °×°×
 *
 */
public final class TrapFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        int targetCount = feature.getImpact();
        List <CardInfo> victims = defender.getField().pickRandom(targetCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature).isAttackable()) {
                continue;
            }
            if (Randomizer.roll100() <= 65) {
                ui.addCardStatus(attacker, victim, feature, CardStatusItem.trapped());
                victim.addStatus(CardStatusItem.trapped());
            }
        }
    }
}
