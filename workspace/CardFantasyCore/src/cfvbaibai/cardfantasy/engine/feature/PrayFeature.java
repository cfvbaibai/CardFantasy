package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class PrayFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo healer) {
        if (healer == null) {
            return;
        }
        int healHP = feature.getImpact();
        Player healee = healer.getOwner();
        if (healHP + healee.getLife() > healee.getMaxLife()) {
            healHP = healee.getMaxLife() - healee.getLife();
        }
        if (healHP == 0) {
            return;
        }
        try {
            resolver.attackHero(healer, healee, feature, healHP);
        } catch (HeroDieSignal e) {
            throw new CardFantasyRuntimeException("Cannot kill hero by Pray!");
        }
    }
}
