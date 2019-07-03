package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public class UnbendingAwaken {
    public static boolean apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card) {
        int awakenCount = skillUseInfo.getSkill().getImpact();
        int fieldCount = card.getOwner().getField().getAliveCards().size();
        List<CardStatusItem> items = card.getStatus().getStatusOf(CardStatusType.不屈);
        if (!items.isEmpty()) {
            return false;
        }
        if(fieldCount>awakenCount)
        {
            return false;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(card, skill, true);
        if (card.getHP() == 0) {
            ui.adjustHP(card, card, 1, skill);
            card.setBasicHP(1);
        }
        CardStatusItem statusItem = CardStatusItem.unbending(skillUseInfo);
        ui.addCardStatus(card, card, skill, statusItem);
        card.addStatus(statusItem);
        return true;
    }

    public static boolean applyMore(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card) {
        int awakenCount = skillUseInfo.getSkill().getImpact();
        int fieldCount = card.getOwner().getField().getAliveCards().size();
        List<CardStatusItem> items = card.getStatus().getStatusOf(CardStatusType.不屈);
        if (!items.isEmpty()) {
            return false;
        }
        if(fieldCount<awakenCount)
        {
            return false;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(card, skill, true);
        if (card.getHP() == 0) {
            ui.adjustHP(card, card, 1, skill);
            card.setBasicHP(1);
        }
        CardStatusItem statusItem = CardStatusItem.unbending(skillUseInfo);
        ui.addCardStatus(card, card, skill, statusItem);
        card.addStatus(statusItem);
        return true;
    }

    public static boolean applyLess(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card,Player attacker) {
        int awakenCount = skillUseInfo.getSkill().getImpact();
        int attackFieldCount = attacker.getField().getAliveCards().size();
        int fieldCount = card.getOwner().getField().getAliveCards().size();
        List<CardStatusItem> items = card.getStatus().getStatusOf(CardStatusType.不屈);
        if (!items.isEmpty()) {
            return false;
        }
        if(attackFieldCount<awakenCount+awakenCount)
        {
            return false;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(card, skill, true);
        if (card.getHP() == 0) {
            ui.adjustHP(card, card, 1, skill);
            card.setBasicHP(1);
        }
        CardStatusItem statusItem = CardStatusItem.unbending(skillUseInfo);
        ui.addCardStatus(card, card, skill, statusItem);
        card.addStatus(statusItem);
        return true;
    }

    public static boolean applyCardName(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card,String cardName) {
        List<CardStatusItem> items = card.getStatus().getStatusOf(CardStatusType.不屈);
        if (!items.isEmpty()) {
            return false;
        }
        Player defender = card.getOwner();
        boolean flag = false;
        for(CardInfo cardInfo:defender.getField().getAliveCards()){
            if(cardInfo!=null){
                if(cardInfo.getName().equals(cardName)){
                    flag = true;
                }
            }
        }
        if(!flag){
            return  flag;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(card, skill, true);
        if (card.getHP() == 0) {
            ui.adjustHP(card, card, 1, skill);
            card.setBasicHP(1);
        }
        CardStatusItem statusItem = CardStatusItem.unbending(skillUseInfo);
        ui.addCardStatus(card, card, skill, statusItem);
        card.addStatus(statusItem);
        return true;
    }

    public static boolean applyCount(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card) {
        List<CardStatusItem> items = card.getStatus().getStatusOf(CardStatusType.不屈);
        if (!items.isEmpty()) {
            return false;
        }
        int impact = skillUseInfo.getSkill().getImpact();
        if(skillUseInfo.getSkillNumber()==0)
        {
            return false;
        }
        if(skillUseInfo.getSkillNumber()==-1)
        {
            skillUseInfo.setSkillNumber(impact);
        }
        skillUseInfo.setSkillNumber(skillUseInfo.getSkillNumber()-1);
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(card, skill, true);
        if (card.getHP() == 0) {
            ui.adjustHP(card, card, 1, skill);
            card.setBasicHP(1);
        }
        CardStatusItem statusItem = CardStatusItem.unbending(skillUseInfo);
        ui.addCardStatus(card, card, skill, statusItem);
        card.addStatus(statusItem);
        return true;
    }
    public static void resetCount( SkillUseInfo skillUseInfo, CardInfo card) throws HeroDieSignal {
        int impact = skillUseInfo.getSkill().getImpact();
        skillUseInfo.setSkillNumber(impact);
    }

    public static void isSkillEscaped(SkillResolver resolver, EntityInfo attacker, Skill attackSkill, CardInfo victim, OnAttackBlockingResult result) {
        List<CardStatusItem> items = victim.getStatus().getStatusOf(CardStatusType.不屈);
        if (items.isEmpty()) {
            return;
        }
        Skill skill = items.get(0).getCause().getSkill();
        if (attackSkill != null) {
            if (Escape.isSkillEscaped(resolver, skill, attackSkill, attacker, victim)) {
                result.setAttackable(false);
            }
        }
    }
    
    public static boolean isStatusEscaped(SkillResolver resolver, CardStatusItem item, CardInfo victim) {
        List<CardStatusItem> items = victim.getStatus().getStatusOf(CardStatusType.不屈);
        if (items.isEmpty()) {
            return false;
        }
        Skill skill = items.get(0).getCause().getSkill();
        return Escape.isStatusEscaped(skill, resolver, item, victim);
    }
}
