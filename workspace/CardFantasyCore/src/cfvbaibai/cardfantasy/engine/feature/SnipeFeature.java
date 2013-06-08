package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Snipe - ¾Ñ»÷
 * Activated after normal attack, apply extra unavoidable, unblockable damage
 * to opponent's cards with least HP on field. 
 * 
 * Snipe cannot be blocked by any defense features like Immue or Dodge.
 * Dying features could be activated by death caused by Snipe.
 * @author °×°×
 *
 */
public final class SnipeFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defenderPlayer) {
        int damage = feature.getImpact();
        CardInfo victim = null;
        for (CardInfo defender : defenderPlayer.getField()) {
            if (defender == null) {
                continue;
            }
            if (victim == null || victim.getHP() > defender.getHP()) {
                victim = defender;
            }
        }
        if (victim != null) {
            resolver.getStage().getUI().useSkill(attacker, victim, feature);
            resolver.getStage().getUI().attackCard(attacker, victim, feature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDeathFeature(attacker, victim, feature);
            }
        }
    }
}
