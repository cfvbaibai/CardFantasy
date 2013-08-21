package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

/**
 * Defensive CardFeature
 * Give 20*level damage to attacker.
 * Unavoidable.
 * 
 * Cannot be blocked by Immue or Dodge.
 * @author °×°×
 *
 */
public final class CounterAttackFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        int damage = cardFeature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardFeature, true);
        ui.attackCard(defender, attacker, cardFeature, damage);
        if (resolver.applyDamage(attacker, damage).cardDead) {
            resolver.resolveDeathFeature(defender, attacker, cardFeature);
        }
    }
}
