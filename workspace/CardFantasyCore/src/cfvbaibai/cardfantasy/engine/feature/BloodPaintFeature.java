package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class BloodPaintFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender, int victimCount) throws HeroDieSignal {
        int damage = feature.getImpact();
        List <CardInfo> victims = defender.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage(); 
            ui.attackCard(attacker, victim, feature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            ui.healCard(attacker, attacker, feature, damage);
            resolver.applyDamage(attacker, -damage);
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, feature);
            }
        }
    }
}
