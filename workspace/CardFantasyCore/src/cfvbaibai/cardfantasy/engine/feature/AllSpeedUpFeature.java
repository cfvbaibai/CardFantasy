package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class AllSpeedUpFeature {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker) {
        int summonDelayOffset = skillUseInfo.getFeature().getImpact(); 
        List<CardInfo> allHandCards = attacker.getOwner().getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, skillUseInfo.getFeature(), true);
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
