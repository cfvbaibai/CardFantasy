package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class EscapeFeature {
    public static boolean isFeatureEscaped(FeatureResolver resolver, Feature feature, Feature attackFeature,
            CardInfo attacker, CardInfo defender) {
        if (attackFeature.getType().containsTag(FeatureTag.ÏİÚå)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, feature);
            ui.blockFeature(attacker, defender, feature, attackFeature);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStatusEscaped(Feature feature, FeatureResolver resolver, CardStatusItem item,
            CardInfo defender) {
        if (item.getType() == CardStatusType.±ù¶³ || item.getType() == CardStatusType.Âé±Ô) {
            CardInfo attacker = item.getCause().getOwner();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, feature);
            ui.blockStatus(attacker, defender, feature, item);
            return true;
        } else {
            return false;
        }
    }
}
