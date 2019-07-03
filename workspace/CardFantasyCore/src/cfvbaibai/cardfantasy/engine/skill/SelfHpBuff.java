package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class SelfHpBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,
            SkillEffectType effectType) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        int impact2 = skill.getImpact();
        int number = impact;
        if(skillUseInfo.getSkillNumber()==-1) {
            skillUseInfo.setSkillNumber(impact);
        }else {
            number = skillUseInfo.getSkillNumber() + impact;
            if(number >=impact2){
                number = impact2;
            }
            skillUseInfo.setSkillNumber(number);
        }
        if (card.getEffectsCausedBy(skillUseInfo).isEmpty()) {
            resolver.getStage().getUI().useSkill(card, skill, true);
            if (effectType == SkillEffectType.ATTACK_CHANGE) {
                resolver.getStage().getUI().adjustAT(card, card, number, skill);
            } else if (effectType == SkillEffectType.MAXHP_CHANGE) {
                resolver.getStage().getUI().adjustHP(card, card, number, skill);
            } else {
                throw new CardFantasyRuntimeException("Invalid effect type: " + effectType.name());
            }
            card.addEffect(new SkillEffect(effectType, skillUseInfo, number, false));
        }

    }
}
