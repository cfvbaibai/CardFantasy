package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class EscapeFeature {
    public static boolean isFeatureEscaped(FeatureResolver resolver, Feature cardFeature, Feature attackFeature,
            EntityInfo attacker, CardInfo defender) {
        if (attackFeature.getType().containsTag(FeatureTag.ÏÝÚå)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature);
            ui.blockFeature(attacker, defender, cardFeature, attackFeature);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStatusEscaped(Feature cardFeature, FeatureResolver resolver, CardStatusItem item,
            CardInfo defender) {
        if (item.getType() == CardStatusType.±ù¶³ || item.getType() == CardStatusType.Âé±Ô) {
            EntityInfo attacker = item.getCause().getOwner();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature);
            ui.blockStatus(attacker, defender, cardFeature, item);
            return true;
        } else {
            return false;
        }
    }
}
