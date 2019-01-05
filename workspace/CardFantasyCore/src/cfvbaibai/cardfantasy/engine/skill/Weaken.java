package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

/**
 * Decrease defender 10 * level AT if normal attack causes damage.
 * 
 * Can be blocked by Immue.
 */
public final class Weaken {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        List<CardInfo> defenders = new ArrayList<CardInfo>();
        defenders.add(defender);
        weakenCard(resolver, skillUseInfo, skill.getImpact(), attacker, defenders,false);
    }

    public static int weakenCard(SkillResolver resolver, SkillUseInfo skillUseInfo, int attackToWeaken, EntityInfo attacker,
            List<CardInfo> defenders,boolean attackSkillFlag) throws HeroDieSignal {
        int totalAttackWeakened = 0;
        for (CardInfo defender : defenders) {
            if (defender == null) {
                continue;
            }
            Skill skill = skillUseInfo.getSkill();
            if (!resolver.resolveAttackBlockingSkills(attacker, defender, skill, 1).isAttackable()) {
                continue;
            }
            if(attackSkillFlag){
                int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, defender, skill);
                if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                    if (attacker instanceof CardInfo) {
                        CardInfo attackCard =  (CardInfo)attacker;
                        if(attackCard.isDead())
                        {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else if (!resolver.resolveAttackBlockingSkills(defender,attackCard , skill, 1).isAttackable()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else {
                            int attackWeakened2 = attackToWeaken;
                            if (attackWeakened2 > attackCard.getCurrentAT()) {
                                attackWeakened2 = attackCard.getCurrentAT();
                            }
                            resolver.getStage().getUI().adjustAT(defender, attackCard, -attackWeakened2, skill);
                            List<SkillEffect> effects = attackCard.getEffects();
                            for (SkillEffect effect : effects) {
                                if (effect.getType() == SkillEffectType.ATTACK_CHANGE && effect.getValue() > 0 &&
                                        effect.getCause().getSkill().getType().containsTag(SkillTag.抗削弱)) {
                                    // TODO: 现在只有群攻提升，不过以后会有其它的
                                    if (attackWeakened2 > effect.getValue()) {
                                        attackWeakened2 -= effect.getValue();
                                        effect.setValue(0);
                                    } else {
                                        attackWeakened2 = 0;
                                        effect.setValue(effect.getValue() - attackWeakened2);
                                    }
                                }
                                if (attackWeakened2 == 0) {
                                    break;
                                }
                            }
                            attackCard.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -attackWeakened2, true));
                        }
                    }
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
            }
            int attackWeakened = attackToWeaken;
            if (attackWeakened > defender.getCurrentAT()) {
                attackWeakened = defender.getCurrentAT();
            }

            resolver.getStage().getUI().adjustAT(attacker, defender, -attackWeakened, skill);
            List<SkillEffect> effects = defender.getEffects();
            for (SkillEffect effect : effects) {
                if (effect.getType() == SkillEffectType.ATTACK_CHANGE && effect.getValue() > 0 &&
                        effect.getCause().getSkill().getType().containsTag(SkillTag.抗削弱)) {
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

    public static int weakenCardOfEnergyDrain(SkillResolver resolver, SkillUseInfo skillUseInfo, int attackToWeaken, EntityInfo attacker,
                                 List<CardInfo> defenders) throws HeroDieSignal {
        int totalAttackWeakened = 0;
        for (CardInfo defender : defenders) {
            if (defender == null) {
                continue;
            }
            Skill skill = skillUseInfo.getSkill();
            int attackWeakened = attackToWeaken;
            if (attackWeakened > defender.getCurrentAT()) {
                attackWeakened = defender.getCurrentAT();
            }
            resolver.getStage().getUI().adjustAT(attacker, defender, -attackWeakened, skill);
            List<SkillEffect> effects = defender.getEffects();
            for (SkillEffect effect : effects) {
                if (effect.getType() == SkillEffectType.ATTACK_CHANGE && effect.getValue() > 0 &&
                        effect.getCause().getSkill().getType().containsTag(SkillTag.抗削弱)) {
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
