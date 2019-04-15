package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public class SummonOfIndenture {
    public static void apply(SkillResolver resolver, IndentureInfo indentureInfo) throws HeroDieSignal {
        Player owner = indentureInfo.getOwner();
        CardInfo cardInfo = indentureInfo.getCardInfo();
        List<CardInfo> cardInfos = owner.getField().getAliveCards();
        SkillUseInfo skillUseInfo = indentureInfo.getSkillUseInfo();
        boolean existFlag = false;
        for(CardInfo fileCard:cardInfos){
            if(cardInfo == fileCard){
                existFlag = true;
                break;
            }
            else{
                if(fileCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)){
                    for(CardStatusItem cardStatusItem:fileCard.getStatus().getStatusOf(CardStatusType.召唤)){
                       if(cardStatusItem.getCause() == skillUseInfo){
                           existFlag = true;
                           break;
                       }
                    }
                }
                if(existFlag){
                    break;
                }
            }
        }
        if(existFlag){
            return;
        }
        cardInfo.reset();
        CardStatusItem summonedStatusItem = CardStatusItem.summoned(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(indentureInfo, cardInfo, skillUseInfo.getSkill(), summonedStatusItem);
        cardInfo.addStatus(summonedStatusItem);
        resolver.summonCardIndenture(owner, cardInfo, indentureInfo, true, skillUseInfo.getSkill(),1);
    }
}
