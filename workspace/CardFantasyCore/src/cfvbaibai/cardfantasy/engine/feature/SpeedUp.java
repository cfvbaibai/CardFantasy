package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class SpeedUp {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker) {
        int summonDelayOffset = skillUseInfo.getSkill().getImpact();
        List<CardInfo> allHandCards = attacker.getOwner().getHand().toList();
        CardInfo victim = null;
        for (CardInfo card : allHandCards) {
            if (victim == null || card.getSummonDelay() > victim.getSummonDelay()) {
                victim = card;
            }
        }
        if (victim == null) {
            // No card at hand.
            return;
        }
        int summonDelay = victim.getSummonDelay();
        if (summonDelay < summonDelayOffset) {
            summonDelayOffset = summonDelay;
        }
        if (summonDelayOffset == 0) {
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, victim, skillUseInfo.getSkill(), true);
        resolver.getStage().getUI().increaseSummonDelay(victim, -summonDelayOffset);
        victim.setSummonDelay(summonDelay - summonDelayOffset);
    }
}
