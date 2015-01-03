package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class AllDelay {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        int summonDelayOffset = skillUseInfo.getSkill().getImpact(); 
        List<CardInfo> allHandCards = defender.getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, skillUseInfo.getSkill(), true);
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            resolver.getStage().getUI().increaseSummonDelay(card, summonDelayOffset);
            card.setSummonDelay(summonDelay + summonDelayOffset);
        }
    }
}
