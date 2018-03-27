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
