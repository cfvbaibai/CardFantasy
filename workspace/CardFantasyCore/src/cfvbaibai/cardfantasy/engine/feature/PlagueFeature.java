package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class PlagueFeature {
    public static void apply(FeatureInfo feature, FeatureResolver resolver, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        int damage = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defenderHero.getField().getAliveCards();
        ui.useSkill(attacker, victims, feature);

        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature);
            if (!result.isAttackable()) {
                return;
            }

            ui.attackCard(attacker, victim, feature, damage);
            resolver.applyDamage(victim, damage);
            ui.adjustAT(attacker, victim, -damage, feature);
            victim.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, -damage, true));
        }
    }
}
