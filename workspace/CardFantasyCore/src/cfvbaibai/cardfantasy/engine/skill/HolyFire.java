package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class HolyFire {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, Player defender) {
        if (defender.getGrave().size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        CardInfo victim = resolver.getStage().getRandomizer().pickRandom(
            defender.getGrave().toList(), 1, true, null).get(0);
        ui.useSkill(attacker, victim, cardSkill, true);
        if (SoulSeal.soulSealed(resolver, attacker)) {
            return;
        }
        ui.cardToOutField(defender, victim);
        defender.getGrave().removeCard(victim);
        defender.getOutField().addCard(victim);
    }
}
