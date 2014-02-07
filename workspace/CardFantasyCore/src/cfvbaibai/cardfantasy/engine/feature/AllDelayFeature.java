package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class AllDelayFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        if (attacker.hasUsed(featureInfo)) {
            return;
        }
        List<CardInfo> allHandCards = defender.getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, featureInfo.getFeature(), true);
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay > 0) {
                card.setSummonDelay(summonDelay + 1);
            }
        }
        attacker.setUsed(featureInfo);
    }
}
