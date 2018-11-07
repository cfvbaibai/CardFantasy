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
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,CardInfo summonCard, Race race,
                             SkillEffectType effectType) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        int impactAdd = 0;
        boolean flag = false;

        Field field = card.getOwner().getField();
        List<CardInfo> allies = resolver.getAdjacentCards(field, card.getPosition());
        if(!allies.contains(summonCard))
        {
            return;
        }
        if(card == summonCard)
        {
            flag = true;
        }
        if(flag) {
            for (CardInfo ally : allies) {
                // IMPORTANT: 种族BUFF无视种族改变技能的影响
                if (race != null && ally.getOriginalRace() != race) {
                    continue;
                }
                resolver.getStage().getUI().useSkill(card, skill, true);
                if (effectType == SkillEffectType.ATTACK_CHANGE) {
                    impactAdd = ally.getLevel0AT() * impact / 100;
                    resolver.getStage().getUI().adjustAT(card, ally, impactAdd, skill);
                } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                    impactAdd = ally.getMaxHP() * impact / 100;
                    resolver.getStage().getUI().adjustHP(card, ally, impactAdd, skill);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                }
                ally.addCoefficientEffect(new SkillEffect(effectType, skillUseInfo, impactAdd, false));

            }
        }
        else{
            // IMPORTANT: 种族BUFF无视种族改变技能的影响
            if (race != null && summonCard.getOriginalRace() != race) {
                return;
            }
            if(allies.contains(summonCard)){
                resolver.getStage().getUI().useSkill(card, skill, true);
                if (effectType == SkillEffectType.ATTACK_CHANGE) {
                    impactAdd = summonCard.getInitAT() * impact / 100;
                    resolver.getStage().getUI().adjustAT(card, summonCard, impactAdd, skill);
                } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                    impactAdd = summonCard.getMaxHP() * impact / 100;
                    resolver.getStage().getUI().adjustHP(card, summonCard, impactAdd, skill);
                } else {
                    throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                }
                summonCard.addCoefficientEffect(new SkillEffect(effectType, skillUseInfo, impactAdd, false));
            }
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        for (CardInfo ally : card.getOwner().getField().getAliveCards()) {
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
