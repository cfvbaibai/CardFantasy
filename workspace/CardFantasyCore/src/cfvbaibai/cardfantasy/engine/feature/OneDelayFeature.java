package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class OneDelayFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        int summonDelayOffset = featureInfo.getFeature().getImpact();
        List<CardInfo> allHandCards = defender.getHand().toList();
        CardInfo victim = null;
        for (CardInfo card : allHandCards) {
            if (victim == null || card.getSummonDelay() < victim.getSummonDelay()) {
                victim = card;
            }
        }
        if (victim == null) {
            // No card at hand.
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, victim, featureInfo.getFeature(), true);
        resolver.getStage().getUI().increaseSummonDelay(victim, summonDelayOffset);
        victim.setSummonDelay(victim.getSummonDelay() + summonDelayOffset);
    }
}
