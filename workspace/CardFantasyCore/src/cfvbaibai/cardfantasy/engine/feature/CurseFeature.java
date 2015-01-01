package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class CurseFeature {
    public static void apply(SkillResolver resolver, Skill cardFeature, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        int damage = cardFeature.getImpact();
        resolver.attackHero(attacker, defenderHero, cardFeature, damage);
    }
}
