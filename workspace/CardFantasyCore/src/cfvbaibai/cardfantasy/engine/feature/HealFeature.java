package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class HealFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo healer) {
        if (healer == null) {
            return;
        }
        int healHP = feature.getImpact();
        CardInfo healee = resolver.pickHealee(healer);
        if (healee == null) {
            return;
        }
        if (healHP + healee.getHP() > healee.getMaxHP()) {
            healHP = healee.getMaxHP() - healee.getHP();
        }
        if (healHP == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(healer, healee, feature);
        // TODO: аяик
        ui.healCard(healer, healee, feature, healHP);
        resolver.applyDamage(healee, -healHP);
    }
}
