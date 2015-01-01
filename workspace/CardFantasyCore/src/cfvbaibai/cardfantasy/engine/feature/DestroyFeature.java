package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class DestroyFeature {
    public static void apply(FeatureResolver resolver, Skill cardFeature, CardInfo attacker, Player defenderHero,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defenderHero.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardFeature, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, cardFeature, 1);
            if (!result.isAttackable()) {
                return;
            }
            ui.killCard(attacker, victim, cardFeature);
            if (resolver.applyDamage(victim, victim.getHP()).cardDead) {
                resolver.resolveDeathFeature(attacker, victim, cardFeature);
            } else {
                throw new CardFantasyRuntimeException(String.format("%s Cannot kill card %s by %s",
                        attacker.getShortDesc(), victim.getShortDesc(), cardFeature.getShortDesc()));
            }
        }
    }
}
