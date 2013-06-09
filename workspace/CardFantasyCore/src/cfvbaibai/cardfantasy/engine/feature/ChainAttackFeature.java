package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class ChainAttackFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker, CardInfo defender)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        // Prevent stack overflow...
        if (!attacker.getEffectsCausedBy(feature).isEmpty()) {
            return;
        }
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo victim : defender.getOwner().getField().getAliveCards()) {
            if (victim == defender || !CardInfo.isSameType(victim, defender)) {
                continue;
            }
            victims.add(victim);
        }
        if (victims.isEmpty()) {
            return;
        }

        GameUI ui = resolver.getStage().getUI();

        ui.useSkill(attacker, victims, feature);

        int chainAT = feature.getImpact() * attacker.getAT() / 100;
        int adjAT = chainAT - attacker.getAT();
        FeatureEffect effect = new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, adjAT, false);
        ui.adjustAT(attacker, attacker, adjAT, feature);
        attacker.addEffect(effect);
        for (CardInfo victim : victims) {
            resolver.attackCard(attacker, victim);
        }
        ui.loseAdjustATEffect(attacker, effect);
        attacker.removeEffect(effect);
    }
}
