package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
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
        for (SkillUseInfo attackerSkillUseInfo : attacker.getAllUsableSkillsIgnoreSilence()) {
            if (attackerSkillUseInfo.getType().containsTag(SkillTag.不动)) {
                skillBlocked = Immobility.isSkillBlocked(resolver, attackerSkillUseInfo.getSkill(), cardSkill, defender, attacker);
            } else if (attackerSkillUseInfo.getType() == SkillType.无效) {
                skillBlocked = NoEffect.isSkillBlocked(resolver, attackerSkillUseInfo.getSkill(), cardSkill, defender, attacker);
            }
        }
        if (!skillBlocked) {
            if (attacker.getOwner().getField().getAliveCards().contains(attacker)) {
                // 横扫击中多个反射装甲的敌方卡牌时，可能导致多次反射
                Return.returnCard(resolver, cardSkill, defender, attacker);
            }
        }
    }
}
