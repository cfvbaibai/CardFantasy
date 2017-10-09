package cfvbaibai.cardfantasy.engine.skill;


import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.CardInfo;

import java.util.ArrayList;
import java.util.List;

public final class MoonBounty {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        CardInfo frontCard = null;
        if(card.getPosition() >0) {
             frontCard = card.getOwner().getField().getCard(card.getPosition()-1);
        }
        List<CardInfo> addCard=new ArrayList<CardInfo>();
        addCard.add(card);
        if(!(null ==frontCard))
        {
            addCard.add(frontCard);
        }
        for (CardInfo thisCard : addCard) {
            cardSkill.setGiveSkill(1);
            if(thisCard.containsUsableSkill(cardSkill.getType())){
                continue;
            }
            thisCard.addSkill(cardSkill);
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
