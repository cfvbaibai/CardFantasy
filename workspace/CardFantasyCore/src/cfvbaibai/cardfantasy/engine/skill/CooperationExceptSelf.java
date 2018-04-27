package cfvbaibai.cardfantasy.engine.skill;


import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

import java.util.ArrayList;
import java.util.List;

public final class CooperationExceptSelf {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,String conCard,boolean flag) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();

        Skill skill2 = skillUseInfo.getAttachedUseInfo2().getSkill();//添加技能
        CardSkill cardSkill2 = new CardSkill(skill2.getType(), skill2.getLevel(), 0, false, false, false, false);
        List<CardInfo> addCard=new ArrayList<CardInfo>();
        for(CardInfo fieldCard :card.getOwner().getField().getAliveCards())
        {
            //去除选定的卡牌
            if(!fieldCard.getName().equals(conCard))
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
        for (CardInfo thisCard : addCard) {
            if(thisCard.containsUsableSkill(cardSkill2.getType())){
                continue;
            }
            thisSkillUserInfo = new SkillUseInfo(thisCard,cardSkill2);
            thisSkillUserInfo.setGiveSkill(1);
            thisCard.addSkill(thisSkillUserInfo);
        }
    }
}
