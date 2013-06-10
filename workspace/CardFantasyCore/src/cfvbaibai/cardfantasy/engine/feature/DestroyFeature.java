package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class DestroyFeature {
    public static void apply(FeatureResolver resolver, Feature feature, CardInfo attacker, Player defenderHero,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = defenderHero.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature);
            if (!result.isAttackable()) {
                return;
            }
            ui.killCard(attacker, victim, feature);
            if (resolver.applyDamage(victim, victim.getHP()).cardDead) {
                resolver.resolveDeathFeature(attacker, victim, feature);
            } else {
                throw new CardFantasyRuntimeException(String.format("%s Cannot kill card %s by %s",
                        attacker.getShortDesc(true), victim.getShortDesc(true), feature.getShortDesc()));
            }
        }
    }
}
