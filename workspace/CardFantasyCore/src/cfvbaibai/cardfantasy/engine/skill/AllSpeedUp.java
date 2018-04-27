package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class AllSpeedUp {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker) {
        int summonDelayOffset = skillUseInfo.getSkill().getImpact(); 
        List<CardInfo> allHandCards = attacker.getOwner().getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, skillUseInfo.getSkill(), true);
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            int summonDelayOffsetReally = summonDelayOffset;
            if (summonDelay < summonDelayOffset) {
                summonDelayOffsetReally = summonDelay;
            }
            if (summonDelayOffsetReally == 0) {
                continue;
            }
            resolver.getStage().getUI().increaseSummonDelay(card, -summonDelayOffsetReally);
            card.setSummonDelay(summonDelay - summonDelayOffsetReally);
        }
    }
}
