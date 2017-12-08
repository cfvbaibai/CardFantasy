package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.List;

public final class CountryBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, CardInfo summorCard, Race race,
                             SkillEffectType effectType, String... summonedCardsDescs) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> summonCardCandidates = null;
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(summorCard.getOwner());
        int impact = skill.getImpact();
        int impactAdd = 0;
        boolean flag = false;
        if (card == summorCard) {
            flag = true;
        }
        Field field = card.getOwner().getField();
        if (flag) {
            for (CardInfo ally : field.getAliveCards()) {
                // IMPORTANT: 种族BUFF无视种族改变技能的影响
                if (ally == card || race != null && ally.getOriginalRace() != race) {
                    continue;
                }
                if (!judgeCountry(summonCardCandidates, card)) {
                    continue;
                }
                if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                    resolver.getStage().getUI().useSkill(card, skill, true);
                    if (effectType == SkillEffectType.ATTACK_CHANGE) {
                        impactAdd = ally.getInitAT() * impact / 100;
                        resolver.getStage().getUI().adjustAT(card, ally, impactAdd, skill);
                    } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                        impactAdd = ally.getBasicMaxHP() * impact / 100;
                        resolver.getStage().getUI().adjustHP(card, ally, impactAdd, skill);
                    } else {
                        throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
                    }
                    ally.addCoefficientEffect(new SkillEffect(effectType, skillUseInfo, impactAdd, false));
                }
            }
        } else {
            // IMPORTANT: 种族BUFF无视种族改变技能的影响
            if (race != null && summorCard.getOriginalRace() != race) {
                return;
            }
            if (!judgeCountry(summonCardCandidates, card)) {
                return;
            }
            resolver.getStage().getUI().useSkill(card, skill, true);
            if (effectType == SkillEffectType.ATTACK_CHANGE) {
                impactAdd = summorCard.getInitAT() * impact / 100;
                resolver.getStage().getUI().adjustAT(card, summorCard, impactAdd, skill);
            } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                impactAdd = summorCard.getBasicMaxHP() * impact / 100;
                resolver.getStage().getUI().adjustHP(card, summorCard, impactAdd, skill);
            } else {
                throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
            }
            summorCard.addCoefficientEffect(new SkillEffect(effectType, skillUseInfo, impactAdd, false));
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

    public static boolean judgeCountry(List<CardInfo> summonCardCandidates, CardInfo card) {
        boolean isCountry = false;
        for (CardInfo thiscard : summonCardCandidates) {
            if (thiscard.getName().equals(card.getName())) {
                isCountry = true;
                break;
            }
        }
        return isCountry;
    }
}
