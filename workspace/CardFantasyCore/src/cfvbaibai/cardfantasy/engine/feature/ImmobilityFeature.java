package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class ImmobilityFeature {
    public static boolean isFeatureBlocked(FeatureResolver resolver, Feature cardFeature, Feature attackFeature,
            EntityInfo attacker, CardInfo defender) {
        if (attackFeature.getType().containsTag(FeatureTag.º¥À¿)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature);
            ui.blockFeature(attacker, defender, cardFeature, attackFeature);
            return true;
        } else {
            return false;
        }
    }
}
