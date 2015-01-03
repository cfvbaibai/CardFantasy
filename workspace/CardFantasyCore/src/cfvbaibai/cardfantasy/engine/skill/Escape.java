package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Escape {
    public static boolean isSkillEscaped(SkillResolver resolver, Skill cardSkill, Skill attackSkill, EntityInfo attacker, CardInfo defender) {
        if (attackSkill.getType().containsTag(SkillTag.控制)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardSkill, true);
            ui.blockSkill(attacker, defender, cardSkill, attackSkill);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStatusEscaped(Skill cardSkill, SkillResolver resolver, CardStatusItem item, CardInfo defender) {
        if (item.getType() == CardStatusType.冰冻 ||
            item.getType() == CardStatusType.麻痹) {
            EntityInfo attacker = item.getCause().getOwner();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardSkill, true);
            ui.blockStatus(attacker, defender, cardSkill, item);
            return true;
        } else {
            return false;
        }
    }
}
