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
        int damage = feature.getImpact();
        List <CardInfo> victims = defender.getField().pickRandom(3, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature).isAttackable()) {
                continue;
            }
            ui.attackCard(attacker, victim, feature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            resolver.resolveCounterAttackFeature(attacker, victim, feature);
            if (cardDead){
                resolver.resolveDeathFeature(attacker, victim, feature);
            } else if (Randomizer.roll100() <= 40) {
                ui.addCardStatus(attacker, victim, feature, CardStatusItem.paralyzed());
                victim.addStatus(CardStatusItem.paralyzed());
            }
        }
    }
}
