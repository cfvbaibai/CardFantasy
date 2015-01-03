package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class Destroy {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defenderHero.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, cardSkill, 1);
            if (!result.isAttackable()) {
                return;
            }
            ui.killCard(attacker, victim, cardSkill);
            if (resolver.applyDamage(victim, victim.getHP()).cardDead) {
                resolver.resolveDeathSkills(attacker, victim, cardSkill);
            } else {
                throw new CardFantasyRuntimeException(String.format("%s Cannot kill card %s by %s",
                        attacker.getShortDesc(), victim.getShortDesc(), cardSkill.getShortDesc()));
            }
        }
    }
}
