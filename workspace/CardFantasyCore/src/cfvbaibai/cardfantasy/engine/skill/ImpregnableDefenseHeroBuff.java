package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class ImpregnableDefenseHeroBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        Player atacter = card.getOwner();
        int coefficient = atacter.getCoefficient()*impact/100;
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, card, skill,true);
        atacter.setCoefficient(coefficient);
    }
    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        Player atacter = card.getOwner();
        int coefficient = atacter.getCoefficient()*100/impact;
        atacter.setCoefficient(coefficient);
    }
}
