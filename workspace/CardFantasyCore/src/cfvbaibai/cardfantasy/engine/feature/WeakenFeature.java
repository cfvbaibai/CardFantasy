package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

/**
 * Decrease defender 10 * level AT if normal attack causes damage.
 * 
 * Can be blocked by Immue.
 * 
 * @author °×°×
 * 
 */
public final class WeakenFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        resolver.getStage().getUI().useSkill(attacker, defender, feature);
        List<CardInfo> defenders = new ArrayList<CardInfo>();
        defenders.add(defender);
        weakenCard(resolver, featureInfo, attacker, defenders);
    }

    public static void weakenCard(FeatureResolver resolver, FeatureInfo featureInfo, EntityInfo attacker,
            List<CardInfo> defenders) throws HeroDieSignal {
        for (CardInfo defender : defenders) {
            if (defender == null) {
                continue;
            }
            Feature feature = featureInfo.getFeature();
            if (!resolver.resolveAttackBlockingFeature(attacker, defender, feature, 1).isAttackable()) {
                continue;
            }
            int attackWeakened = feature.getImpact();
            if (attackWeakened > defender.getAT()) {
                attackWeakened = defender.getAT();
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

            defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, -attackWeakened, true));
        }
    }
}
