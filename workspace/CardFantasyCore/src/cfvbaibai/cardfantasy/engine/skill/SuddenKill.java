package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class SuddenKill {
    public static boolean isBlockSkillDisabled(SkillResolver resolver,
            Skill counterBlockSkill, Skill blockSkill, CardInfo attacker, CardInfo defender) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        if (defender == null) {
            throw new CardFantasyRuntimeException("defender is null");
        }
        if (defender.getHP() >= defender.getMaxHP() / 2) {
            return false;
        }
        if (defender.getHP() < attacker.getCurrentAT()) {
            return false;
        }
        if (blockSkill.getType().containsTag(SkillTag.物理护甲) && blockSkill.getType() != SkillType.闪避) {
            resolver.getStage().getUI().useSkill(attacker, defender, counterBlockSkill, true);
            resolver.getStage().getUI().disableBlock(attacker, defender, counterBlockSkill, blockSkill);
            return true;
        } else {
            return false;
        }
    }

    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker,
            CardInfo defender, OnAttackBlockingResult blockingResult) {
        if (defender.getHP() >= defender.getMaxHP() / 2) {
            return;
        }
        if (defender.getHP() < attacker.getCurrentAT()) {
            return;
        }
        if (defender.getRace() == Race.BOSS) {
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, defender, skillUseInfo.getSkill(), true);
        blockingResult.setDamage(defender.getHP());
    }
}
