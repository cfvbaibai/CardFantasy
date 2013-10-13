package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class ManaErodeFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = defender.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardFeature, true);
        int damage = cardFeature.getImpact();
        for (CardInfo victim : victims) {
            if (victim.containsUsableFeature(FeatureType.免疫) ||
                victim.containsUsableFeature(FeatureType.法力反射)) {
                // TODO: ui.damageUp
                damage *= 3;
            }
            ui.attackCard(attacker, victim, cardFeature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, cardFeature);
            }
        }
    }
}
