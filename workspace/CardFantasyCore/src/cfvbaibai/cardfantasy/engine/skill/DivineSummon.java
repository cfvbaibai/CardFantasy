package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class DivineSummon {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker) throws HeroDieSignal {
        List<CardInfo> allDeckCards = attacker.getOwner().getDeck().toList();
        CardInfo target = null;
        for (CardInfo card : allDeckCards) {
            if (target == attacker) {
                continue;
            }
            if (target == null || card.getSummonDelay() > target.getSummonDelay()) {
                target = card;
            }
        }
        if (target == null) {
            // No card in deck.
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, target, skillUseInfo.getSkill(), true);
        resolver.summonCard(attacker.getOwner(), target, null, false, skillUseInfo.getSkill());
        CardStatusItem item = CardStatusItem.weak(skillUseInfo);
        target.addStatus(item);
    }
}
