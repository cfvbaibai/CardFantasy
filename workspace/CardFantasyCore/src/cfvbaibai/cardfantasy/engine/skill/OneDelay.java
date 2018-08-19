package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class OneDelay {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender) {
        int summonDelayOffset = skillUseInfo.getSkill().getImpact();
        List<CardInfo> allHandCards = defender.getHand().toList();
        CardInfo victim = null;
        if(!resolver.resolveStopDelay(defender))
        {
            return;
        }
        for (CardInfo card : allHandCards) {
            if (victim == null || card.getSummonDelay() < victim.getSummonDelay()) {
                victim = card;
            }
        }
        if (victim == null) {
            // No card at hand.
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, victim, skillUseInfo.getSkill(), true);
        resolver.getStage().getUI().increaseSummonDelay(victim, summonDelayOffset);
        victim.setSummonDelay(victim.getSummonDelay() + summonDelayOffset);
    }
}
