package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

/**
 * Defensive CardSkill
 * Give 20*level damage to attacker.
 * Unavoidable.
 * 
 * Cannot be blocked by Immue or Dodge.
 */
public final class CounterAttack {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            int attackDamage) throws HeroDieSignal {
        if (attackDamage <= 0) {
            return;
        }
        if (attacker == null) {
            return;
        }
        if (defender.isDead()) {
            return;
        }
        int damage = cardSkill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        if (!resolver.resolverCounterAttackBlockSkill(cardSkill, attacker, defender)) {
            ui.attackCard(defender, attacker, cardSkill, damage);
            resolver.resolveDeathSkills(defender, attacker, cardSkill, resolver.applyDamage(attacker, cardSkill, damage));
        }
    }
}
