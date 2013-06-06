package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

/**
 * Defensive Feature
 * Give 20*level damage to attacker.
 * Unavoidable.
 * 
 * Cannot be blocked by Immue or Dodge.
 * @author °×°×
 *
 */
public final class CounterAttackFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) {
        if (attacker == null) {
            return;
        }
        int damage = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature);
        ui.attackCard(defender, attacker, feature, damage);
        if (resolver.applyDamage(attacker, damage).cardDead) {
            resolver.resolveDyingFeature(attacker, defender, feature);
        }
    }
}
