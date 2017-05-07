package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

/**
 * Created by hasee on 2017/5/7.
 */
public class GiveSideSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> allies = resolver.getAdjacentCards(field, card.getPosition());
        for (CardInfo ally : allies) {
            cardSkill.setGiveSkill(1);
            ally.addSkill(cardSkill);
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,Skill addSkill) {
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        for (CardInfo ally : card.getOwner().getField().toList()) {
            if (ally == null) { continue; }
            ally.removeSkill(cardSkill);
        }
    }
}
