package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public class Silence {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo caster, Player defenderHero, boolean isTargetAll, boolean onlyCastOnce) throws HeroDieSignal {
        if (onlyCastOnce) {
            if (resolver.getStage().hasUsed(skillUseInfo)&&resolver.getStage().hasPlayerUsed(skillUseInfo.getOwner().getOwner())) {
                return;
            }
            if(caster instanceof CardInfo){
                CardInfo sumCard = (CardInfo) caster;
                if(sumCard.isSummonedMinion()) {
                    SkillUseInfo sumSkillInfo = new SkillUseInfo(sumCard.getStatus().getStatusOf(CardStatusType.召唤).get(0).getCause().getOwner(), sumCard.getStatus().getStatusOf(CardStatusType.召唤).get(0).getCause().getSkill());
                    if (resolver.getStage().hasUsed(sumSkillInfo)) {
                        return;
                    }
                }
            }
        }
        List<CardInfo> victims = new ArrayList<CardInfo>();
        if (isTargetAll) {
            victims.addAll(defenderHero.getField().getAliveCards());
        } else {
            CardInfo casterCard = (CardInfo) caster;
            CardInfo victim = defenderHero.getField().getCard(casterCard.getPosition());
            if (victim == null || victim.isDead()) {
                return;
            }
            victims.add(victim);
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(caster, victims, skill, true);
        for (CardInfo victim : victims) {
            CardStatusItem statusItem = CardStatusItem.slience(skillUseInfo);
            if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(caster, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if(caster instanceof CardInfo)
                {
                    CardInfo casterCard = (CardInfo) caster;
                    if(casterCard.isDead())
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else if (!resolver.resolveAttackBlockingSkills(victim, casterCard, skill, 1).isAttackable()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        ui.addCardStatus(victim, casterCard, skill, statusItem);
                        casterCard.addStatus(statusItem);
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            ui.addCardStatus(caster, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
        if (onlyCastOnce) {
            if(caster instanceof CardInfo){
                CardInfo summoncard = (CardInfo) caster;
                if(summoncard.isSummonedMinion()) {
                    SkillUseInfo summonSkillInfo = new SkillUseInfo(summoncard.getStatus().getStatusOf(CardStatusType.召唤).get(0).getCause().getOwner(), summoncard.getStatus().getStatusOf(CardStatusType.召唤).get(0).getCause().getSkill());
                    resolver.getStage().setUsed(summonSkillInfo, true);
                }
            }
            if(!resolver.getStage().hasUsed(skillUseInfo)) {
                resolver.getStage().setUsed(skillUseInfo, true);
            }
            resolver.getStage().setPlayerUsed(skillUseInfo.getOwner().getOwner(), true);
        }
    }
}
