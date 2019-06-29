package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Rapture {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero) throws HeroDieSignal {

        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        int victimCount = skillUseInfo.getSkill().getImpact();
        int effectNumber = skillUseInfo.getSkill().getImpact2();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), victimCount, true, null);
        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attackCard, victims, skill, true);
        CardStatusItem statusItem = CardStatusItem.Rapture(skillUseInfo);
        statusItem.setEffectNumber(effectNumber);
        boolean flag = digestionCard(resolver,skillUseInfo,defenderHero);
        if(!flag){
            return;
        }
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attackCard, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(victim.containsAllSkill(SkillType.离魂芳印) || victim.containsAllSkill(SkillType.斗者)
                    || victim.containsAllSkill(SkillType.时空封印) || victim.containsAllSkill(SkillType.吞噬)
                    || victim.containsAllSkill(SkillType.生死界限))
            {
                continue;
            }
            ui.addCardStatus(attackCard, victim, skill, statusItem);
            victim.addStatus(statusItem);
            defenderHero.getField().removeCard(victim);
            resolver.resolveLeaveSkills(victim);
            ui.returnCard(attackCard, victim, skill);
            if(victim.isSummonedMinion())
            {
                return;
            }
            defenderHero.getOutField().addCard(victim);
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero) throws HeroDieSignal {
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = random.pickRandom(defenderHero.getOutField().getAllCards(), -1, true, null);
        for(CardInfo outCard:victims) {
            List<CardStatusItem> raptureStatusItems = outCard.getStatus().getStatusOf(CardStatusType.离魂);
            if(raptureStatusItems.size()>0) {
                for(CardStatusItem cardStatusItem:raptureStatusItems) {
                    if(cardStatusItem.getCause() == skillUseInfo) {
                        outCard.restoreOwner();
                        outCard.reset();
                        defenderHero.getOutField().removeCard(outCard);
                        if (!defenderHero.getHand().isFull()) {
                            outCard.getOwner().getHand().addCard(outCard);
                            ui.cardToHand(outCard.getOwner(), outCard);
                        } else {
                            outCard.getOwner().getDeck().addCard(outCard);
                            ui.cardToDeck(outCard.getOwner(), outCard);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static boolean digestionCard(SkillResolver resolver, SkillUseInfo skillUseInfo,Player defenderHero){
        boolean result = true; //是否消化完毕
        for (CardInfo outCard : defenderHero.getOutField().getAllCards()) {
            List<CardStatusItem> raptureStatusItems = outCard.getStatus().getStatusOf(CardStatusType.离魂);
            if(raptureStatusItems.size()>0) {
                for(CardStatusItem cardStatusItem:raptureStatusItems) {
                    if(cardStatusItem.getCause() == skillUseInfo) {
                        if(cardStatusItem.getEffectNumber()>1){
                            result = false;
                        }
                        resolver.removeStatus(outCard, CardStatusType.离魂);
                    }
                }
            }
        }
        return result;
    }
}
