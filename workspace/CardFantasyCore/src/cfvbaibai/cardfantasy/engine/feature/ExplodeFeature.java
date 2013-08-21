package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class ExplodeFeature {
    /**
     * 
     * @param resolver
     * @param cardFeature
     * @param attacker The card which attacks the feature owner.
     * @param exploder The card which is attacked and tries to activate Explode feature.
     * @throws HeroDieSignal 
     */
    public static void apply(FeatureResolver resolver, Feature cardFeature, EntityInfo attacker, CardInfo exploder) throws HeroDieSignal {

        int damage = cardFeature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = resolver.getAdjacentCards(attacker.getOwner().getField(), exploder.getPosition());
        for (CardInfo victim : victims) {
            if (victim == null) {
                continue;
            }
            ui.useSkill(exploder, victim, cardFeature, true);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, cardFeature, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(exploder, victim, cardFeature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDeathFeature(exploder, victim, cardFeature);
            }            
        }
    }
}
