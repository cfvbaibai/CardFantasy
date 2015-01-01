package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.FeatureType;
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
 */
public final class WeakenFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        List<CardInfo> defenders = new ArrayList<CardInfo>();
        defenders.add(defender);
        weakenCard(resolver, featureInfo, skill.getImpact(), attacker, defenders);
    }

    public static int weakenCard(FeatureResolver resolver, FeatureInfo featureInfo, int attackToWeaken, EntityInfo attacker,
            List<CardInfo> defenders) throws HeroDieSignal {
        int totalAttackWeakened = 0;
        for (CardInfo defender : defenders) {
            if (defender == null) {
                continue;
            }
            Skill skill = featureInfo.getFeature();
            if (!resolver.resolveAttackBlockingFeature(attacker, defender, skill, 1).isAttackable()) {
                continue;
            }
            int attackWeakened = attackToWeaken;
            if (attackWeakened > defender.getCurrentAT()) {
                attackWeakened = defender.getCurrentAT();
            }

            resolver.getStage().getUI().adjustAT(attacker, defender, -attackWeakened, skill);
            List<FeatureEffect> effects = defender.getEffects();
            for (FeatureEffect effect : effects) {
                if (effect.getType() == FeatureEffectType.ATTACK_CHANGE && effect.getValue() > 0 &&
                        effect.getCause().getType() == FeatureType.群攻提升) {
                    // TODO: 现在只有群攻提升，不过以后会有其它的
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
            totalAttackWeakened += attackWeakened;
        }
        return totalAttackWeakened;
    }
}
