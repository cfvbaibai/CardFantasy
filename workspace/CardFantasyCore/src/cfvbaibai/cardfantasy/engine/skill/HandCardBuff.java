package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class HandCardBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, SkillEffectType effectType, int number) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        int impactAdd = 0;
        List<CardInfo> allHandCards = card.getOwner().getHand().toList();
        List<CardInfo> addCard = new ArrayList<CardInfo>();
        addCard = Randomizer.getRandomizer().pickRandom(allHandCards, number, true, null);
        for (CardInfo ally : addCard) {
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
    }
}
