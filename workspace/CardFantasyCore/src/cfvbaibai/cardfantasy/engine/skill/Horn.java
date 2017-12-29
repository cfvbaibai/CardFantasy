package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import java.util.List;

public final class Horn {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker) throws HeroDieSignal {
        List<CardInfo> allHandCards = attacker.getOwner().getHand().toList();
        CardInfo target = null;
        for (CardInfo card : allHandCards) {
            if (target == attacker) {
                continue;
            }
            if (target == null || card.getSummonDelay() > target.getSummonDelay()) {
                target = card;
            }
        }
        if (target == null) {
            // No card in Hand.
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, target, skillUseInfo.getSkill(), true);
        resolver.summonCard(attacker.getOwner(), target, null, false, skillUseInfo.getSkill());
        CardStatusItem item = CardStatusItem.weak(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(attacker, target, skillUseInfo.getSkill(), item);
        target.addStatus(item);
    }
}
