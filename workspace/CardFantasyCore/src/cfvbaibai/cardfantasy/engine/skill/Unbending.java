package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Unbending {
    public static boolean apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card) {
        if (card.hasUsed(skillUseInfo)) {
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
        card.setUsed(skillUseInfo);
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
