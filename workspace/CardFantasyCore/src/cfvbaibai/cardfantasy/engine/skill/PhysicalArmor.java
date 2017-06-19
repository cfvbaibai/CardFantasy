package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class PhysicalArmor {
    public static int apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        if (resolver.resolveCounterBlockSkill(cardSkill, attacker, defender)) {
            return originalDamage;
        }
        if (resolver.resolveStopBlockSkill(cardSkill, attacker, defender)) {
            return originalDamage;
        }
        int reduceDamage = cardSkill.getImpact();
        int actualDamage = originalDamage*(100-reduceDamage)/100;
        ui.blockDamage(defender, attacker, defender, cardSkill, originalDamage, actualDamage);
        return actualDamage;
    }
}
