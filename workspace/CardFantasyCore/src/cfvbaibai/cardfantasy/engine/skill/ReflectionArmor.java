package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class ReflectionArmor {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, Skill attackSkill, int attackDamage)
            throws HeroDieSignal {
        if (attackDamage <= 0) {
            return;
        }
        if (attacker == null || attacker.isDead() || defender.isSilent()) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(defender, attacker, cardSkill, 1);
        if (!result.isAttackable()) {
            return;
        }
        for (SkillUseInfo attackerSkillUseInfo : attacker.getAllUsableSkillsIgnoreSilence()) { 
            if (attackerSkillUseInfo.getType().containsTag(SkillTag.不动)) { 
                if (Immobility.isSkillBlocked(resolver, attackerSkillUseInfo.getSkill(), cardSkill, defender, attacker)) {
                    return;
                }
            } 
        } 
        Return.returnCard(resolver, cardSkill, defender, attacker);
    }
}
