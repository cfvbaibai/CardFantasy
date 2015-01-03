package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Immue {
    public static boolean isSkillBlocked(SkillResolver resolver, Skill cardSkill, Skill attackSkill,
            EntityInfo attacker, CardInfo defender) {
        if (!attackSkill.getType().containsTag(SkillTag.抗免疫)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardSkill, true);
            ui.blockSkill(attacker, defender, cardSkill, attackSkill);
            return true;
        } else {
            return false;
        }
    }
}
