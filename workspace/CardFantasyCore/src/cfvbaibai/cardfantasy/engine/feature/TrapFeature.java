package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Trap 1*level enemy card at 65% probability.
 * 
 * Can be blocked by Immue
 * 
 * @author °×°×
 * 
 */
public final class TrapFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        Feature feature = featureInfo.getFeature();
        int targetCount = feature.getImpact();
        List<CardInfo> victims = defender.getField().pickRandom(targetCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature, 1).isAttackable()) {
                continue;
            }
            if (resolver.getStage().getRandomizer().roll100(65)) {
                CardStatusItem status = CardStatusItem.trapped(featureInfo);
                ui.addCardStatus(attacker, victim, feature, status);
                victim.addStatus(status);
            }
        }
    }
}
