package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class WeakenAllFeature {

    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker, Player defenderPlayer) throws HeroDieSignal {
        if (defenderPlayer == null) {
            throw new CardFantasyRuntimeException("defenderPlayer is null");
        }
        if (attacker == null) {
            return;
        }
        List<CardInfo> defenders = defenderPlayer.getField().getAliveCards();
        resolver.getStage().getUI().useSkill(attacker, defenders, feature);
        WeakenFeature.weakenCard(resolver, feature, attacker, defenders);
    }
}
