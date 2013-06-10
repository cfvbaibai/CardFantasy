package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class CounterMagicFeature {

    public static boolean isFeatureBlocked(FeatureResolver resolver, Feature feature, Feature attackFeature, CardInfo attacker,
            CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return false;
        }
        if (attackFeature.getType().containsTag(FeatureTag.Ä§·¨)) {
            int damage = feature.getImpact();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, feature);
            ui.attackCard(defender, attacker, feature, damage);
            if (resolver.applyDamage(attacker, damage).cardDead) {
                resolver.resolveDeathFeature(defender, attacker, feature);
            }
            return true;
        } else {
            return false;
        }
    }
}
