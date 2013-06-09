package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class WoundFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) {
        if (normalAttackDamage <= 0) {
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, defender, feature);
        CardStatusItem status = CardStatusItem.wound(feature);
        resolver.getStage().getUI().addCardStatus(attacker, defender, feature, status);
        defender.addStatus(status);
    }
}
