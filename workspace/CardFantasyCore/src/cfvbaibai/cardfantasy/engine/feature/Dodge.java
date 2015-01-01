package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Dodge {
    public static boolean apply(Skill cardFeature, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int dodgeRate = cardFeature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        boolean bingo = resolver.getStage().getRandomizer().roll100(dodgeRate);
        ui.useSkill(defender, attacker, cardFeature, bingo);
        if (bingo) {
            if (resolver.resolveCounterBlockFeature(cardFeature, attacker, defender)) {
                return false;
            }
            ui.blockDamage(defender, attacker, defender, cardFeature, originalDamage, 0);
            return true;
        } else {
            return false;
        }
    }
}
