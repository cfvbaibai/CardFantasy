package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class AllSpeedUpFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker) {
        int summonDelayOffset = featureInfo.getFeature().getImpact(); 
        List<CardInfo> allHandCards = attacker.getOwner().getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, featureInfo.getFeature(), true);
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay < summonDelayOffset) {
                summonDelayOffset = summonDelay;
            }
            if (summonDelayOffset == 0) {
                continue;
            }
            resolver.getStage().getUI().increaseSummonDelay(card, -summonDelayOffset);
            card.setSummonDelay(summonDelay - summonDelayOffset);
        }
    }
}
