package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class GuardFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo guardian, int damage) {
        if (attacker == null) {
            return;
        }
        resolver.getStage().getUI().useSkill(guardian, attacker, feature);
        resolver.getStage().getUI().attackCard(attacker, guardian, feature, damage);
        if (resolver.applyDamage(guardian, damage).cardDead) {
            resolver.resolveDyingFeature(attacker, guardian, feature);
        }
    }
}
