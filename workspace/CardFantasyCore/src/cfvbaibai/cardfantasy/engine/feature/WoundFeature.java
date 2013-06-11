package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class WoundFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) {
        if (normalAttackDamage <= 0) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        resolver.getStage().getUI().useSkill(attacker, defender, feature);
        CardStatusItem status = CardStatusItem.wound(featureInfo);
        resolver.getStage().getUI().addCardStatus(attacker, defender, feature, status);
        defender.addStatus(status);
    }
}
