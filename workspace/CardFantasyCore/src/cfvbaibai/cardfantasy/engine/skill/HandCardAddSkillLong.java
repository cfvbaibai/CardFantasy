package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

import java.util.ArrayList;
import java.util.List;

public class HandCardAddSkillLong {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill) {
        if (card == null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        List<CardInfo> allHandCards = card.getOwner().getHand().toList();
        CardInfo addCard = null;
        boolean flag = true;
        for (CardInfo handCard : allHandCards) {
            for (SkillUseInfo skillInfo : handCard.getSkillUserInfos()) {
                if (skillInfo.getGiveSkill() == 2) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                flag = true;
                continue;
            } else {
                if (addCard == null) {
                    addCard = handCard;
                } else {
                    if (addCard.getSummonDelay() < handCard.getSummonDelay()) {
                        addCard = handCard;
                    }
                }
            }
        }
        if(addCard==null)
        {
            return;
        }
        if (addCard.containsAllSkill(addSkill.getType())) {
            return;
        }
        SkillUseInfo thisSkillUserInfo = null;
        thisSkillUserInfo = new SkillUseInfo(addCard, cardSkill);
        thisSkillUserInfo.setGiveSkill(2);
        addCard.addSkill(thisSkillUserInfo);
    }
}
