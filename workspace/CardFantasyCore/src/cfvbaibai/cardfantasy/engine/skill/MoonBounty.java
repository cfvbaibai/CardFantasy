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
        SkillUseInfo thisSkillUserInfo = null;
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
            if(thisCard.containsUsableSkill(cardSkill.getType())){
                continue;
            }
            thisSkillUserInfo = new SkillUseInfo(thisCard,cardSkill);
            thisSkillUserInfo.setGiveSkill(1);
            thisCard.addSkill(thisSkillUserInfo);
        }
    }
    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,Skill addSkill) {
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        SkillUseInfo thisSkillUserInfo = null;
        for (CardInfo ally : card.getOwner().getField().getAliveCards()) {
            if (ally == null) { continue; }
            thisSkillUserInfo = new SkillUseInfo(ally,cardSkill);
            thisSkillUserInfo.setGiveSkill(1);
            ally.removeSkill(thisSkillUserInfo);
        }
    }
}
