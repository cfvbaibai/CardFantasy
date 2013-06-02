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
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        int damage = feature.getLevel() * 25;
        for (CardInfo card : defender.getField()) {
            if (card == null) {
                continue;
            }
            resolver.getStage().getUI().attackCard(attacker, card, feature, damage);
            resolver.applyDamage(card, damage);
        }
    }
}
