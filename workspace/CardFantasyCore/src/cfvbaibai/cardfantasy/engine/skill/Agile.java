package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class Agile {
    public static boolean isCounterAttackSkillDisabled(SkillResolver resolver, Skill agileSkill,
        Skill counterAttackSkill, CardInfo attacker, CardInfo counterAttacker) {
    if (attacker == null) {
        throw new CardFantasyRuntimeException("attacker is null");
    }
    if (counterAttacker == null) {
        throw new CardFantasyRuntimeException("counterAttacker is null");
    }
    if (counterAttackSkill.getType().containsTag(SkillTag.反击)) {
        resolver.getStage().getUI().useSkill(attacker, counterAttacker, agileSkill, true);
        resolver.getStage().getUI().disableBlock(attacker, counterAttacker, agileSkill, counterAttackSkill);
        return true;
    } else {
        return false;
    }
}
}
