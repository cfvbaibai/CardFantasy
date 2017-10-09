package cfvbaibai.cardfantasy.engine.skill;


import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

//给两个手牌添加技能
public class HandCardAddSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        List<CardInfo> allHandCards = card.getOwner().getHand().toList();
        CardInfo oneCard = null;
        CardInfo twoCard = null;
        List<CardInfo> addCard=new ArrayList<CardInfo>();
        for (CardInfo ally : allHandCards) {
            if (oneCard != null){
                if (ally.getSummonDelay() < oneCard.getSummonDelay()) {
                    twoCard = oneCard;
                    oneCard = ally;
                }
                else if (twoCard != null) {
                    if (ally.getSummonDelay()<oneCard.getSummonDelay()) {
                        twoCard = ally;
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
        for (CardInfo once : addCard) {
            if (once.containsAllUsableSkillsWithTag(SkillTag.抗沉默)&&addSkill.getType().containsTag(SkillTag.抗沉默)) {
                continue;
            }
            cardSkill.setGiveSkill(1);
            if (once.containsUsableSkill(cardSkill.getType())){
                continue;
            }
            once.addSkill(cardSkill);
        }
    }

    //移除的结算在死亡移除buff时完成了。
//    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,Skill addSkill) {
//        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
//        for (CardInfo ally : card.getOwner().getField().toList()) {
//            if (ally == null) { continue; }
//            ally.removeSkill(cardSkill);
//        }
//    }

//    public static void removeAll(SkillResolver resolver,SkillUseInfo skillUseInfo,CardInfo card) {
//        card.removeAllGiveSkill();
//    }
}
