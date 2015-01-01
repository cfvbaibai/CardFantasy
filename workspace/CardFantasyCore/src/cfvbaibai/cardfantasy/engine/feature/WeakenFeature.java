package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

/**
 * Decrease defender 10 * level AT if normal attack causes damage.
 * 
 * Can be blocked by Immue.
 */
public final class WeakenFeature {
    public static void apply(FeatureResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        List<CardInfo> defenders = new ArrayList<CardInfo>();
        defenders.add(defender);
        weakenCard(resolver, skillUseInfo, skill.getImpact(), attacker, defenders);
    }

    public static int weakenCard(FeatureResolver resolver, SkillUseInfo skillUseInfo, int attackToWeaken, EntityInfo attacker,
            List<CardInfo> defenders) throws HeroDieSignal {
        int totalAttackWeakened = 0;
        for (CardInfo defender : defenders) {
            if (defender == null) {
                continue;
            }
            Skill skill = skillUseInfo.getFeature();
            if (!resolver.resolveAttackBlockingFeature(attacker, defender, skill, 1).isAttackable()) {
                continue;
            }
            int attackWeakened = attackToWeaken;
            if (attackWeakened > defender.getCurrentAT()) {
                attackWeakened = defender.getCurrentAT();
            }

            resolver.getStage().getUI().adjustAT(attacker, defender, -attackWeakened, skill);
            List<SkillEffect> effects = defender.getEffects();
            for (SkillEffect effect : effects) {
                if (effect.getType() == SkillEffectType.ATTACK_CHANGE && effect.getValue() > 0 &&
                        effect.getCause().getType() == SkillType.群攻提升) {
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

            defender.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -attackWeakened, true));
            totalAttackWeakened += attackWeakened;
        }
        return totalAttackWeakened;
    }
}
