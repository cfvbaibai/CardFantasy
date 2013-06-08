package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;

/**
 * Decrease defender 10 * level AT if normal attack causes damage.
 * 
 * Can be blocked by Immue.
 * 
 * @author °×°×
 * 
 */
public final class WeakenFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        int attackWeakened = feature.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, feature);
        if (!resolver.resolveAttackBlockingFeature(attacker, defender, feature).attackable) {
            return;
        }
        resolver.getStage().getUI().adjustAT(attacker, defender, -attackWeakened, feature);
        List<FeatureEffect> effects = defender.getEffects();
        for (FeatureEffect effect : effects) {
            if (effect.getType() == FeatureEffectType.ATTACK_CHANGE && effect.getValue() > 0) {
                if (attackWeakened > effect.getValue()) {
                    attackWeakened -= effect.getValue();
                    effect.setValue(0);
                } else {
                    attackWeakened = 0;
                    effect.setValue(effect.getValue() - attackWeakened);
                }
            }
            if (attackWeakened == 0) {
                break;
            }
        }

        defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, -attackWeakened));
    }
}
