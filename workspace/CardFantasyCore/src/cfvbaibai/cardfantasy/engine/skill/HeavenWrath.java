package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class HeavenWrath {
    public static void apply(SkillResolver resolver, Skill cardSkill, EntityInfo attacker, Player defenderHero)
            throws HeroDieSignal {
        int aliveEnemyCardCount = defenderHero.getField().getAliveCards().size();
        if (aliveEnemyCardCount == 0) {
            return;
        }
        int damage = cardSkill.getImpact() * aliveEnemyCardCount;
        resolver.attackHero(attacker, defenderHero, cardSkill, damage);
    }
}
