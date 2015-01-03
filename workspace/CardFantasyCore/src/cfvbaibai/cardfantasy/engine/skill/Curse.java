package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class Curse {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        int damage = cardSkill.getImpact();
        resolver.attackHero(attacker, defenderHero, cardSkill, damage);
    }
}
