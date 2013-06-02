package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Penetration give enemy hero 15% of the normal attack damage.
 * @author °×°×
 *
 * Cannot be blocked by Immue.
 */
public final class PenetrationFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, Player defender, int normalAttackDamage)
            throws HeroDieSignal {
        int damage = (int) (normalAttackDamage * 0.15);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkillToHero(attacker, defender, feature);
        resolver.attackHero(attacker, defender, feature, damage);
    }
}
