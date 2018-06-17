package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.StageInfo;

public class HellFire {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender, int victimCount)
            throws HeroDieSignal {
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        Skill cardSkill  = skillUseInfo.getAttachedUseInfo1().getSkill();
        GameUI ui = stage.getUI();
        Skill skill = skillUseInfo.getAttachedUseInfo2().getSkill();
        int damage = skill.getImpact();
        List<CardInfo> candidates = resolver.getStage().getRandomizer().pickRandom(
                defender.getField().toList(), victimCount, true, null);
        CardStatusItem newBurningStatus = CardStatusItem.burning(damage, skillUseInfo);
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo candidate : candidates) {
            int damage2 = random.next(cardSkill.getImpact(), cardSkill.getImpact() * 2 + 1);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, candidate, cardSkill, damage2);
            if (!result.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, candidate, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard = (CardInfo) attacker;
                    if (attackCard.isDead()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        int damage3=damage2;
                        OnAttackBlockingResult result3 = resolver.resolveAttackBlockingSkills(candidate, attackCard, skill, damage3);
                        if (!result3.isAttackable()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else{
                            damage3 = result3.getDamage();
                            ui.attackCard(candidate, attackCard, skill, damage2);
                            resolver.resolveDeathSkills(candidate, attackCard, skill, resolver.applyDamage(candidate, attackCard, skill, damage2));
                            boolean skipped2 = false;
                            for (CardStatusItem existingBurningStatus : candidate.getStatus().getStatusOf(CardStatusType.燃烧)) {
                                if (existingBurningStatus.getEffect() == newBurningStatus.getEffect()) {
                                    skipped2 = true;
                                    break;
                                }
                            }
                            if (!skipped2) {
                                if (magicEchoSkillResult == 1) {
                                    continue;
                                }
                            }
                            else{
                                ui.useSkill(candidate, attackCard, skill, true);
                                OnAttackBlockingResult result4 = resolver.resolveAttackBlockingSkills(candidate, attackCard, skill, damage);
                                if (result4.isAttackable()) {
                                    ui.addCardStatus(candidate, attackCard, skill, newBurningStatus);
                                    attackCard.addStatus(newBurningStatus);
                                }
                            }
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            damage2 = result.getDamage();
            ui.attackCard(attacker, candidate, cardSkill, damage2);
            resolver.resolveDeathSkills(attacker, candidate, cardSkill, resolver.applyDamage(attacker, candidate, cardSkill, damage2));
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
//                victims.add(candidate);
                continue;
            }
            ui.useSkill(attacker, candidate, skill, true);
            OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(attacker, candidate, skill, damage);
            if (result2.isAttackable()) {
                ui.addCardStatus(attacker, candidate, skill, newBurningStatus);
                candidate.addStatus(newBurningStatus);
            }
        }

//        for (CardInfo victim : victims) {
//            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
//            if (!result.isAttackable()) {
//                continue;
//            }
//            ui.addCardStatus(attacker, victim, skill, newBurningStatus);
//            victim.addStatus(newBurningStatus);
//        }
    }
}
