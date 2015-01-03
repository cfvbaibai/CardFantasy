package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class IceArmor {
    public static int apply(Skill cardFeature, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardFeature, true);
        if (resolver.resolveCounterBlockSkill(cardFeature, attacker, defender)) {
            return originalDamage;
        }
        
        int maxDamage = cardFeature.getImpact();
        int actualDamage = originalDamage;
        if (actualDamage > maxDamage) {
            actualDamage = maxDamage;
        }

        ui.blockDamage(defender, attacker, defender, cardFeature, originalDamage, actualDamage);
        return actualDamage;
    }
}
