package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class ReflectionArmor {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, Skill attackSkill, int attackDamage)
            throws HeroDieSignal {
        if (attackDamage <= 0) {
            return;
        }
        if (attacker == null) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        boolean skillBlocked = false;
        for (SkillUseInfo attackerSkillUseInfo : attacker.getUsableNormalSkills()) {
            if (attackerSkillUseInfo.getType() == SkillType.不动) {
                skillBlocked = Immobility.isSkillBlocked(resolver, attackerSkillUseInfo.getSkill(), cardSkill, defender, attacker);
            }
        }
        if (!skillBlocked) {
            Return.returnCard(resolver, cardSkill, defender, attacker);
        }
    }
}
