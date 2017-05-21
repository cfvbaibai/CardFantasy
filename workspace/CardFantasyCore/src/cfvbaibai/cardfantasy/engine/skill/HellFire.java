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
            damage2 = result.getDamage();
            if (attacker instanceof CardInfo) {
                resolver.resolveCounterAttackSkills((CardInfo)attacker, candidate, cardSkill, result, null);
            }
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
                victims.add(candidate);
            }
        }
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            ui.addCardStatus(attacker, victim, skill, newBurningStatus);
            victim.addStatus(newBurningStatus);
        }
    }
}
