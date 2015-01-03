package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Penetration give enemy hero 15% of the normal attack damage.
 * Cannot be blocked by Immue.
 */
public final class Penetration {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, Player defender, int normalAttackDamage)
            throws HeroDieSignal {
        if (normalAttackDamage <= 0) {
            return;
        }
        int damage = normalAttackDamage * cardSkill.getImpact() / 100;
        resolver.attackHero(attacker, defender, cardSkill, damage);
    }
}
