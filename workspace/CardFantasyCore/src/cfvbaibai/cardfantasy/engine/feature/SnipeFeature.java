package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class SnipeFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        int damage = feature.getLevel() * 25;
        for (CardInfo card : defender.getField()) {
            if (card == null) {
                continue;
            }
            if (resolver.resolveAttackBlockingFeature(attacker, card, feature).isBlocked) {
                continue;
            }
            resolver.getStage().getUI().attackCard(attacker, card, feature, damage);
            if (resolver.applyDamage(card, damage).cardDead) {
                resolver.resolveDyingFeature(attacker, card, feature);
            }
        }
        
    }
}
