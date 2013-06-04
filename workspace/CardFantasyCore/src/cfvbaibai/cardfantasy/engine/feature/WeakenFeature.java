package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

/**
 * Decrease defender 10 * level AT if normal attack causes damage.
 * 
 * Can be blocked by Immue.
 * @author °×°×
 *
 */
public final class WeakenFeature {
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int normalAttackDamage) {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        int attackWeakened = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, feature);
        if (!resolver.resolveAttackBlockingFeature(attacker, defender, feature).attackable) {
            return;
        }
        resolver.getStage().getUI().adjustAT(defender, -attackWeakened, feature);
        defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature.getType(), -attackWeakened));
    }
}
