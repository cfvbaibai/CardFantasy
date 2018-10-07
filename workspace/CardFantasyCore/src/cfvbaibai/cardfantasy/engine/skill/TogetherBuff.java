package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class TogetherBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Race race) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        int impact2 = skill.getImpact2();
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            // IMPORTANT: 种族BUFF无视种族改变技能的影响
            if (ally == card || race != null && ally.getOriginalRace() != race) {
                continue;
            }
            if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, skill, true);
                resolver.getStage().getUI().adjustAT(card, ally, impact, skill);
                resolver.getStage().getUI().adjustHP(card, ally, impact2, skill);
                ally.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, impact, false));
                ally.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, impact2, false));
            }
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
