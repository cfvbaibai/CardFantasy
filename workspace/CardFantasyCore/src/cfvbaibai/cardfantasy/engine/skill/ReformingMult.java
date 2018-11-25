package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public class ReformingMult {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,Player defender) throws HeroDieSignal{
        if (card == null || card.isDead())  {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        int impact = skillUseInfo.getSkill().getImpact();
        int number = skillUseInfo.getSkillNumber();
        if(number==0)
        {
            return;
        }
        if(number<0)
        {
            number = impact;
            skillUseInfo.setSkillNumber(impact);
        }
        skillUseInfo.setSkillNumber(number-1);
        Skill skill = skillUseInfo.getSkill();
        resolver.getStage().getUI().useSkill(card, skill, true);
        GameUI ui = resolver.getStage().getUI();
        //重整不重置限定技能
//        for (SkillUseInfo victimSkillUseInfo : card.getAllUsableSkillsIgnoreSilence()) {
//            resolver.getStage().removeUsed(victimSkillUseInfo,skillUseInfo.getOwner().getOwner(),defender);
//        }
        ui.useSkill(card, card, skillUseInfo.getSkill(), true);
        Player attackPlayer = card.getOwner();
        resolver.killCard(card,card,skill);//改为杀死卡进入墓地
    //    resolver.resolveDeathSkills(card,victim,skillUseInfo.getSkill(),result);//新生可以发动死契
        if(card.getOwner() !=attackPlayer) {
            card.switchOwner(attackPlayer);
        }
        //处理顽强司命情况下，卡牌已经回到场上，不需要再次结算降临技能。
        if(card.isAlive())
        {
            return;
        }
        //强制移除卡牌，防止新生以后出现卡牌复制。
        card.getOwner().getHand().removeCard(card);
        card.getOwner().getDeck().removeCard(card);
        card.getOwner().getField().removeCard(card);
        defender.getHand().removeCard(card);
        defender.getDeck().removeCard(card);
        defender.getField().removeCard(card);
        resolver.summonCard(card.getOwner(), card, null, false, skillUseInfo.getSkill(),0);
        card.setUsed(skillUseInfo);
    }

    public static void reset( SkillUseInfo skillUseInfo, CardInfo card) throws HeroDieSignal {
        int impact = skillUseInfo.getSkill().getImpact();
        skillUseInfo.setSkillNumber(impact);
    }
}
