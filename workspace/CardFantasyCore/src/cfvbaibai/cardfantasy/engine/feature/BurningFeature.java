package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class BurningFeature {
    public static void apply(FeatureInfo feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        int damage = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, feature);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(defender, attacker, feature);
        if (!result.isAttackable()) {
            return;
        }
        if (attacker.getStatus().containsStatusCausedBy(feature, CardStatusType.»º…’)) {
            return;
        }
        CardStatusItem status = CardStatusItem.burning(damage, feature);
        ui.addCardStatus(defender, attacker, feature, status);
        attacker.addStatus(status);
    }
}
