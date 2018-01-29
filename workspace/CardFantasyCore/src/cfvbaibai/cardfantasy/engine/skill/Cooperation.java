package cfvbaibai.cardfantasy.engine.skill;


import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

import java.util.ArrayList;
import java.util.List;

public final class Cooperation {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,String conCard) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        Skill skill1 = skillUseInfo.getAttachedUseInfo1().getSkill();//自身添加技能
        Skill skill2 = skillUseInfo.getAttachedUseInfo1().getSkill();//连携卡牌添加技能
        CardSkill cardSkill1 = new CardSkill(skill1.getType(), skill1.getLevel(), 0, false, false, false, false);
        CardSkill cardSkill2 = new CardSkill(skill2.getType(), skill2.getLevel(), 0, false, false, false, false);
        List<CardInfo> addCard=new ArrayList<CardInfo>();
        for(CardInfo fieldCard :card.getOwner().getField().getAliveCards())
        {
            if(fieldCard.getName().equals(conCard))
            {
                addCard.add(fieldCard);
            }
        }
        if(addCard.size()<1)
        {
            return;
        }
        resolver.getStage().getUI().useSkill(card, skill, true);
        SkillUseInfo thisSkillUserInfo = null;
        SkillUseInfo onceselfSkillUserInfo = null;
        for (CardInfo thisCard : addCard) {
            if(thisCard.containsUsableSkill(cardSkill2.getType())){
                continue;
            }
            thisSkillUserInfo = new SkillUseInfo(thisCard,cardSkill2);
            thisSkillUserInfo.setGiveSkill(1);
            thisCard.addSkill(thisSkillUserInfo);
        }
        onceselfSkillUserInfo = new SkillUseInfo(card,cardSkill1);
        onceselfSkillUserInfo.setGiveSkill(1);
        card.addSkill(thisSkillUserInfo);
    }
}
