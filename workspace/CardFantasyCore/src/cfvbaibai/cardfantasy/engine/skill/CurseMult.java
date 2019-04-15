package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class CurseMult {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,int number) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        int damage = cardSkill.getImpact();
        for(int i=0;i<number;i++)
        {
            if(attacker.isDead())
            {
                break;
            }
            resolver.attackHero(attacker, defenderHero, cardSkill, damage);
        }
    }
}
