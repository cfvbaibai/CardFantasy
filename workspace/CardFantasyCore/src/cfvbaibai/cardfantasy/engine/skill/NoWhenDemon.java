package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class NoWhenDemon {
    private static Skill NO_WDEMON_SKILL = new CardSkill(SkillType.魔族天赋, 0, 0, false, false, false, false);
    public static boolean isSkillBlocked(SkillResolver resolver, Skill attackSkill, EntityInfo attacker, CardInfo defender) {
        if (defender.isDeman() && attackSkill.getType().containsTag(SkillTag.魔族天赋)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, NO_WDEMON_SKILL, true);
            ui.blockSkill(attacker, defender, NO_WDEMON_SKILL, attackSkill);
            return true;
        } else {
            return false;
        }
    }
}
