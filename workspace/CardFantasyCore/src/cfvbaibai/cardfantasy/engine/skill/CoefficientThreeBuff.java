package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.data.Race;

public final class CoefficientThreeBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Race race,
                             SkillEffectType effectType) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> allies = resolver.getAdjacentCards(field, card.getPosition());
        for (CardInfo ally : allies) {
            // IMPORTANT: 种族BUFF无视种族改变技能的影响
            if ( race != null && ally.getOriginalRace() != race) {
                continue;
            }
            if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, skill, true);
                if (effectType == SkillEffectType.ATTACK_CHANGE) {
                    impact = ally.getInitAT()*impact/100;
                    resolver.getStage().getUI().adjustAT(card, ally, impact, skill);
                } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                    resolver.getStage().getUI().adjustHP(card, ally, impact, skill);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                }
                ally.addCoefficientEffect(new SkillEffect(effectType, skillUseInfo, impact, false));
            }
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        for (CardInfo ally : card.getOwner().getField().toList()) {
            if (ally == null) {
                continue;
            }
            List<SkillEffect> effects = ally.getEffectsCausedBy(skillUseInfo);
            for (SkillEffect effect : effects) {
                resolver.getStage().getUI().loseAdjustHPEffect(ally, effect);
                ally.removeEffect(effect);
            }
        }
    }
}
