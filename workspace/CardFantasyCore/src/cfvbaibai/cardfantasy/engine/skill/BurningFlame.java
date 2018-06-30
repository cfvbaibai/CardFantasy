package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class BurningFlame {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,int victimCount)
            throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        int damage = skill.getImpact();
        List<CardInfo> candidates = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        CardStatusItem newBurningStatus = CardStatusItem.burning(damage, skillUseInfo);
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo candidate : candidates) {
            /*
             * Burning status of the same level could not be stacked.
             * This seems to be an bug in official version
             */
            boolean skipped = false;
            for (CardStatusItem existingBurningStatus : candidate.getStatus().getStatusOf(CardStatusType.燃烧)) {
                if (existingBurningStatus.getEffect() == newBurningStatus.getEffect()) {
                    skipped = true;
                    break;
                }
            }
            if (!skipped) {
                victims.add(candidate);
            }
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard =  (CardInfo)attacker;
                    if(attackCard.isDead())
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        boolean skipped = false;
                        for (CardStatusItem existingBurningStatus : attackCard.getStatus().getStatusOf(CardStatusType.燃烧)) {
                            if (existingBurningStatus.getEffect() == newBurningStatus.getEffect()) {
                                skipped = true;
                                break;
                            }
                        }
                        if (!skipped) {
                            OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attackCard, skill, damage);
                            if (!result2.isAttackable()) {
                                if (magicEchoSkillResult == 1) {
                                    continue;
                                }
                            }
                            else {
                                ui.addCardStatus(victim, attackCard, skill, newBurningStatus);
                                attackCard.addStatus(newBurningStatus);
                            }
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            ui.addCardStatus(attacker, victim, skill, newBurningStatus);
            victim.addStatus(newBurningStatus);
        }
    }
}
