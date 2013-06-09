package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class ImmobilityFeature {
    public static boolean isFeatureBlocked(FeatureResolver resolver, Feature feature, Feature attackFeature,
            CardInfo attacker, CardInfo defender) {
        if (attackFeature.getType().containsTag(FeatureTag.¼´ËÀ)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, feature);
            ui.blockFeature(attacker, defender, feature, attackFeature);
            return true;
        } else {
            return false;
        }
    }
}
