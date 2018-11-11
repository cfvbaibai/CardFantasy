package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class TogetherBuffOfStar {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,String cardName,int addAttack,int addHp) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
            // IMPORTANT: 对应卡牌二维提升
            if (!ally.getName().equals(cardName)) {
                continue;
            }
            if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, skill, true);
                resolver.getStage().getUI().adjustAT(card, ally, addAttack, skill);
                resolver.getStage().getUI().adjustHP(card, ally, addHp, skill);
                ally.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, addAttack, false));
                ally.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, addHp, false));
            }
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (skillUseInfo == null) {
            throw new CardFantasyRuntimeException("skillUseInfo cannot be null");
        }
        Field field = card.getOwner().getField();
        for (CardInfo ally : field.getAliveCards()) {
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
