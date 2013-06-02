package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatus;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Chain Lightening give 25 * level damages to 3 enemy's cards and 40% probability to cause paralyzed.
 * 
 * Can be blocked by Immue.
 * Can be reflected by Magic Reflection.
 * Can activate dying feature.
 * @author °×°×
 *
 */
public final class ChainLighteningFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        int damage = feature.getLevel() * 25;
        List <CardInfo> victims = defender.getField().pickRandom(3, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (resolver.resolveAttackBlockingFeature(attacker, victim, feature).isBlocked) {
                continue;
            }
            ui.attackCard(attacker, victim, feature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDyingFeature(attacker, victim, feature);
            } else if (Randomizer.roll100() <= 40) {
                ui.changeCardStatus(attacker, victim, feature, CardStatus.paralyzed());
                victim.setStatus(CardStatus.paralyzed());
            }
        }
    }
}
