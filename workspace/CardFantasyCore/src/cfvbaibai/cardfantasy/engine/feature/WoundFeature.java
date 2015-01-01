package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class WoundFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, Skill attackFeature, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) {
        if (normalAttackDamage <= 0) {
            return;
        }
        if (defender.isDead()) {
            return;
        }
        if (attackFeature != null && attackFeature.getType() == FeatureType.横扫) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        CardStatusItem status = CardStatusItem.wound(featureInfo);
        resolver.getStage().getUI().addCardStatus(attacker, defender, skill, status);
        defender.addStatus(status);
    }
}
