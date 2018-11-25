package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.engine.*;

public final class NebulaChain {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker) throws HeroDieSignal {
        if (attacker.hasUsed(skillUseInfo)) {
            return;
        }
        attacker.setUsed(skillUseInfo);
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
        resolver.summonCard(attacker.getOwner(), target, null, false, skillUseInfo.getSkill(),0);
        CardStatusItem item = CardStatusItem.weak(skillUseInfo);
        target.addStatus(item);
    }

    public static void applyMult(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker) throws HeroDieSignal {
        if (attacker.hasUsed(skillUseInfo)) {
            return;
        }
        attacker.setUsed(skillUseInfo);
        int impact = skillUseInfo.getSkill().getImpact();
        for (int i = 0; i < impact; i++) {
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
            resolver.summonCard(attacker.getOwner(), target, null, false, skillUseInfo.getSkill(), 0);
            CardStatusItem item = CardStatusItem.weak(skillUseInfo);
            target.addStatus(item);
        }
    }
}
