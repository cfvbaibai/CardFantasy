package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class HeavenWrathFeature {
    public static void apply(FeatureResolver resolver, Skill cardFeature, EntityInfo attacker, Player defenderHero)
            throws HeroDieSignal {
        int aliveEnemyCardCount = defenderHero.getField().getAliveCards().size();
        if (aliveEnemyCardCount == 0) {
            return;
        }
        int damage = cardFeature.getImpact() * aliveEnemyCardCount;
        resolver.attackHero(attacker, defenderHero, cardFeature, damage);
    }
}
