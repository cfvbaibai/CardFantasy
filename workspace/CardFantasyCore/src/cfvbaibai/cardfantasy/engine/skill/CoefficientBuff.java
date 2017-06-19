package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class CoefficientBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Race race,
                             SkillEffectType effectType) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            // IMPORTANT: 种族BUFF无视种族改变技能的影响
            if (ally == card || race != null && ally.getOriginalRace() != race) {
                continue;
            }
            //生命符文可以无限叠加
//            if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, skill, true);
                if (effectType == SkillEffectType.ATTACK_CHANGE) {
                    resolver.getStage().getUI().adjustAT(card, ally, impact, skill);
                } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().adjustHP(card, ally, impact, skill);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                }
                ally.addCoefficientEffect(new SkillEffect(effectType, skillUseInfo, impact, false));
//            }
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Race race) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (skillUseInfo == null) {
            throw new CardFantasyRuntimeException("skillUseInfo cannot be null");
        }
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            if (ally == card || race != null && ally.getRace() != race) {
                continue;
            }
            for (SkillEffect effect : ally.getEffectsCausedBy(skillUseInfo)) {
                if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                    resolver.getStage().getUI().loseAdjustATEffect(ally, effect);
                } else if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().loseAdjustHPEffect(ally, effect);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effect.getType().name());
                }
                ally.removeEffect(effect);
            }
        }
    }
}
