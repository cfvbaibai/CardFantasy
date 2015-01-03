package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Dodge {
    public static boolean apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int dodgeRate = cardSkill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        boolean bingo = resolver.getStage().getRandomizer().roll100(dodgeRate);
        ui.useSkill(defender, attacker, cardSkill, bingo);
        if (bingo) {
            if (resolver.resolveCounterBlockSkill(cardSkill, attacker, defender)) {
                return false;
            }
            ui.blockDamage(defender, attacker, defender, cardSkill, originalDamage, 0);
            return true;
        } else {
            return false;
        }
    }
}
