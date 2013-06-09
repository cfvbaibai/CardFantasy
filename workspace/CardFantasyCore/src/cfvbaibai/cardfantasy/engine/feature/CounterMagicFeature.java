package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class CounterMagicFeature {

    public static boolean apply(FeatureResolver resolver, Feature feature, Feature attackFeature, CardInfo attacker,
            CardInfo defender) {
        if (attacker == null) {
            return false;
        }
        if (attackFeature.getType().containsTag(FeatureTag.Ä§·¨)) {
            int damage = feature.getImpact();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, feature);
            ui.attackCard(defender, attacker, feature, damage);
            if (resolver.applyDamage(attacker, damage).cardDead) {
                resolver.resolveDeathFeature(attacker, defender, feature);
            }
            return true;
        } else {
            return false;
        }
    }
}
