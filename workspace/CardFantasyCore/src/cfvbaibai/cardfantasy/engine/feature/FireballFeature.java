package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.Player;

public final class FireballFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        int damage = Randomizer.next(feature.getImpact(), feature.getImpact() * 2 + 1);
        List <CardInfo> victims = defender.getField().pickRandom(1, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature).isAttackable()) {
                continue;
            }
            ui.attackCard(attacker, victim, feature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            resolver.resolveCounterAttackFeature(attacker, victim, feature);
            if (cardDead){
                resolver.resolveDeathFeature(attacker, victim, feature);
            }
        }
    }
}
