package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class ExplodeFeature {
    /**
     * 
     * @param resolver
     * @param feature
     * @param attacker The card which attacks the feature owner.
     * @param exploder The card which is attacked and tries to activate Explode feature.
     * @throws HeroDieSignal 
     */
    public static void apply(FeatureResolver resolver, Feature feature, CardInfo attacker, CardInfo exploder) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        int damage = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = resolver.getAdjacentCards(attacker);
        for (CardInfo victim : victims) {
            ui.useSkill(exploder, victim, feature);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(exploder, victim, feature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDeathFeature(exploder, victim, feature);
            }            
        }
    }
}
