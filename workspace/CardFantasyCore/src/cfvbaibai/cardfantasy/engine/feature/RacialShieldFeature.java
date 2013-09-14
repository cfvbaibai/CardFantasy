package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class RacialShieldFeature {
    public static int apply(Feature feature, FeatureResolver resolver, CardInfo attacker, EntityInfo victim,
            EntityInfo blocker, int originalDamage, Race targetRace) {
        if (attacker.getRace() != targetRace) {
            return originalDamage;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(blocker, feature, true);
        int reduction = feature.getImpact();
        int actualDamage = originalDamage - originalDamage * reduction / 100;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        ui.protect(blocker, attacker, victim, null, feature);
        ui.blockDamage(blocker, attacker, victim, feature, originalDamage, actualDamage);
        return actualDamage;
    }
}
