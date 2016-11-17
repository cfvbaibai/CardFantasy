package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class IceArmor {
    public static int apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        if (resolver.resolveCounterBlockSkill(cardSkill, attacker, defender)) {
            return originalDamage;
        }
        
        int maxDamage = cardSkill.getImpact();
        if (cardSkill.getType() == SkillType.魔龙之血 || cardSkill.getType() == SkillType.神魔之甲) {
            maxDamage = cardSkill.getImpact2();
        }
        int actualDamage = originalDamage;
        if (actualDamage > maxDamage) {
            actualDamage = maxDamage;
        }

        ui.blockDamage(defender, attacker, defender, cardSkill, originalDamage, actualDamage);
        return actualDamage;
    }
}
