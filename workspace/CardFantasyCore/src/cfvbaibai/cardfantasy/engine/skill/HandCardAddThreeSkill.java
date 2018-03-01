package cfvbaibai.cardfantasy.engine.skill;


import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

import java.util.ArrayList;
import java.util.List;

//给三个手牌添加技能
public class HandCardAddThreeSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill) {
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        List<CardInfo> allHandCards = card.getOwner().getHand().toList();
        CardInfo oneCard = null;
        CardInfo twoCard = null;
        CardInfo threeCard = null;
        List<CardInfo> addCard = new ArrayList<CardInfo>();
        SkillUseInfo thisSkillUserInfo= null;
        boolean flag = true;
        for (CardInfo ally : allHandCards) {
            for(SkillUseInfo skillInfo:ally.getSkillUserInfos())
            {
                if(skillInfo.getGiveSkill()==2)
                {
                    flag=false;
                    break;
                }
            }
            if(!flag)
            {
                flag =true;
                continue;
            }
            if (oneCard != null) {
                if (ally.getSummonDelay() < oneCard.getSummonDelay()) {
                    threeCard = twoCard;
                    twoCard = oneCard;
                    oneCard = ally;
                }
                else if (twoCard != null) {
                    if (ally.getSummonDelay()<twoCard.getSummonDelay()) {
                        threeCard = twoCard;
                        twoCard = ally;
                    }
                    else if(threeCard !=null)
                    {
                        if (ally.getSummonDelay()<threeCard.getSummonDelay()) {
                            threeCard = ally;
                        }
                    }
                    else{
                        threeCard = ally;
                    }
                } else {
                    twoCard = ally;
                }
            } else {
                oneCard = ally;
            }
        }
        if (oneCard != null) {
            addCard.add(oneCard);
        }
        if (twoCard != null) {
            addCard.add(twoCard);
        }
        if (threeCard != null) {
            addCard.add(threeCard);
        }
        for (CardInfo once : addCard) {
            if (once.containsUsableSkill(cardSkill.getType())){
                continue;
            }
            thisSkillUserInfo = new SkillUseInfo(once,cardSkill);
            thisSkillUserInfo.setGiveSkill(2);
            once.addSkill(thisSkillUserInfo);
        }
    }
}
