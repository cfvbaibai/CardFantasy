package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class HandCardBuffOpponent {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo,CardInfo card, Player defender, SkillEffectType effectType, int number) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        List<CardInfo> allHandCards = defender.getHand().toList();
        List<CardInfo> addCard = new ArrayList<CardInfo>();
        addCard = Randomizer.getRandomizer().pickRandom(allHandCards, number, true, null);
        for (CardInfo ally : addCard) {
            if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                resolver.getStage().getUI().useSkill(card, skill, true);
                if (effectType == SkillEffectType.ATTACK_CHANGE) {
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
}
