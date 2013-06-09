package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class FirewallFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender) {
        FireballFeature.applyFireMagic(feature, resolver, attacker, defender, 3);
    }
}
