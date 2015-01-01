package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

/**
 * Defensive CardFeature
 * Give 20*level damage to attacker.
 * Unavoidable.
 * 
 * Cannot be blocked by Immue or Dodge.
 */
public final class CounterAttackFeature {
    public static void apply(Skill cardFeature, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            int attackDamage) throws HeroDieSignal {
        if (attackDamage <= 0) {
            return;
        }
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
