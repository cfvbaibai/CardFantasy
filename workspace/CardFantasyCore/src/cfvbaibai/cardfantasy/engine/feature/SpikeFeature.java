package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class SpikeFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        int damage = cardFeature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = resolver.getAdjacentCards(attacker.getOwner().getField(), attacker.getPosition());
        ui.useSkill(defender, victims, cardFeature, true);
        for (CardInfo victim : victims) {
            if (victim == null) {
                continue;
            }
            ui.attackCard(defender, victim, cardFeature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDeathFeature(defender, victim, cardFeature);
            }            
        }
    }
}
