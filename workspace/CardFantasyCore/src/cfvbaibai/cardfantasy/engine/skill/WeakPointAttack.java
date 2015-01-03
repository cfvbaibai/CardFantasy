package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class WeakPointAttack {
    public static boolean isBlockSkillDisabled(SkillResolver resolver, Skill attackSkill,
            Skill blockSkill, CardInfo attacker, CardInfo defender) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        if (defender == null) {
            throw new CardFantasyRuntimeException("defender is null");
        }
        if (blockSkill.getType().containsTag(SkillTag.物理护甲)) {
            resolver.getStage().getUI().useSkill(attacker, defender, attackSkill, true);
            resolver.getStage().getUI().disableBlock(attacker, defender, attackSkill, blockSkill);
            return true;
        } else {
            return false;
        }
    }
}
