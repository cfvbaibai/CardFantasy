package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class SpikeFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) {
        if (attacker == null) {
            return;
        }
        int damage = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = resolver.getAdjacentCards(attacker);
        ui.useSkill(defender, victims, feature);
        for (CardInfo victim : victims) {
            ui.attackCard(defender, victim, feature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDeathFeature(defender, victim, feature);
            }            
        }
    }
}
