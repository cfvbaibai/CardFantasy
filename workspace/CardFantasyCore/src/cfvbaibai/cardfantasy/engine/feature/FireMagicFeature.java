package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class FireMagicFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, Player defender, int victimCount) throws HeroDieSignal {
        int damage = resolver.getStage().getRandomizer().next(cardFeature.getImpact(), cardFeature.getImpact() * 2 + 1);
        List <CardInfo> victims = defender.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardFeature);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, cardFeature);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage(); 
            ui.attackCard(attacker, victim, cardFeature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            resolver.resolveCounterAttackFeature(attacker, victim, cardFeature, result);
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, cardFeature);
            }
        }
    }
}
