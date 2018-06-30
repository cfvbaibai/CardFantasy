package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class RaceChange {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo attacker, Player defenderHero) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defenderHero.getField().getAliveCards();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attacker, victim, skill, 1).isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult == 1 || magicEchoSkillResult == 2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard =  (CardInfo)attacker;
                    if (attackCard.isDead()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else{
                        if (!resolver.resolveAttackBlockingSkills(victim, attackCard, skill, 1).isAttackable()) {
                            continue;
                        }
                        else{
                            resolver.removeStatus(attackCard, CardStatusType.王国);
                            resolver.removeStatus(attackCard, CardStatusType.森林);
                            resolver.removeStatus(attackCard, CardStatusType.蛮荒);
                            resolver.removeStatus(attackCard, CardStatusType.地狱);
                            CardStatusItem item = CardStatusItem.raceChange(skillUseInfo);
                            ui.addCardStatus(victim, attackCard, skill, item);
                            attackCard.addStatus(item);
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            resolver.removeStatus(victim, CardStatusType.王国);
            resolver.removeStatus(victim, CardStatusType.森林);
            resolver.removeStatus(victim, CardStatusType.蛮荒);
            resolver.removeStatus(victim, CardStatusType.地狱);
            CardStatusItem item = CardStatusItem.raceChange(skillUseInfo);
            ui.addCardStatus(attacker, victim, skill, item);
            victim.addStatus(item);
        }
    }
}
