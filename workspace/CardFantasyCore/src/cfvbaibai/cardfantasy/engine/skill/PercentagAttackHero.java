package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class PercentagAttackHero {
    public static void apply(SkillResolver resolver, Skill cardSkill, EntityInfo attacker, CardInfo defender)
            throws HeroDieSignal {
        Player defenderHero = defender.getOwner();
        int damage = defenderHero.getMaxHP() * cardSkill.getImpact() / 100;
        resolver.attackHero(attacker, defenderHero, cardSkill, damage);
    }
}
