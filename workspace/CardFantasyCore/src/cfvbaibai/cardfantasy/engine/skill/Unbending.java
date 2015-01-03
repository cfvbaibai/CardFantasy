package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Unbending {
    public static boolean apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card) {
        if (card.hasUsed(skillUseInfo)) {
            return false;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, skillUseInfo.getSkill(), true);
        card.setBasicHP(1);
        CardStatusItem statusItem = CardStatusItem.unbending(skillUseInfo);
        ui.addCardStatus(card, card, skillUseInfo.getSkill(), statusItem);
        card.addStatus(statusItem);
        card.setUsed(skillUseInfo);
        return true;
    }

    public static boolean isEscaped(SkillResolver resolver, EntityInfo attacker, Skill attackSkill, CardInfo victim, int originalDamage) {
        List<CardStatusItem> items = victim.getStatus().getStatusOf(CardStatusType.不屈);
        if (items.isEmpty()) {
            return false;
        }
        Skill skill = items.get(0).getCause().getSkill();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(victim, attacker, skill, true);
        if (attackSkill == null) {
            ui.blockDamage(victim, attacker, victim, skill, originalDamage, 0);
        } else {
            ui.blockSkill(attacker, victim, skill, attackSkill);
        }
        return true;
    }
}
