package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class NoEffect {
    private static Skill NO_EFFECT_SKILL = new CardSkill(SkillType.无效, 0, 0, false, false, false, false);
    public static boolean isSkillBlocked(SkillResolver resolver, Skill attackSkill, EntityInfo attacker, CardInfo defender) {
        if (defender.isBoss() && attackSkill.getType().containsTag(SkillTag.魔王无效)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, NO_EFFECT_SKILL, true);
            ui.blockSkill(attacker, defender, NO_EFFECT_SKILL, attackSkill);
            return true;
        } else {
            return false;
        }
    }
}
