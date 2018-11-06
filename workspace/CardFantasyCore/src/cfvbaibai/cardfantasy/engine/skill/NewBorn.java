package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;

public class NewBorn {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,Player defender,int victimCount) throws HeroDieSignal{
        if (card == null || card.isDead())  {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> frontCards = resolver.getFrontCards(field, card.getPosition());
        List<CardInfo> selectFrontCards =  new ArrayList<CardInfo>();;
        if(frontCards.size()<=0)
        {
            return;
        }
        for(CardInfo victim : frontCards)
        {
            if(!victim.containsAllUsableSkillsWithTag(SkillTag.新生))
            {
                selectFrontCards.add(victim);
            }
        }
        if(selectFrontCards.size()==0)
        {
            return;
        }
        List<CardInfo> victims = random.pickRandom(selectFrontCards , victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();

        for (CardInfo victim : victims) {
            if (!victim.containsAllUsableSkillsWithTag(SkillTag.新生)) {
                for (SkillUseInfo victimSkillUseInfo : victim.getAllUsableSkillsIgnoreSilence()) {
                    resolver.getStage().removeUsed(victimSkillUseInfo,skillUseInfo.getOwner().getOwner(),defender);
                }
                ui.useSkill(card, victim, skillUseInfo.getSkill(), true);
                if (victim.getStatus().containsStatus(CardStatusType.召唤)) {
                    resolver.killCard(card,victim,skill);//改为杀死卡进入墓地
                 //   resolver.resolveDeathSkills(card,victim,skillUseInfo.getSkill(),result);//新生可以发动死契
                    for(CardStatusItem summonedStatusItem:victim.getStatus().getAllItems())
                    {
                        if(summonedStatusItem.getType()==CardStatusType.召唤){
                            Summon.newBornApply(resolver, skillUseInfo, card, SummonType.Summoning, 1,summonedStatusItem, victim.getName());//新生卡牌可以立即攻击。
                            break;
                        }
                    }
                }
                else{
                    resolver.killCard(card,victim,skill);//改为杀死卡进入墓地
                //    resolver.resolveDeathSkills(card,victim,skillUseInfo.getSkill(),result);//新生可以发动死契
                    if(victim.getOwner() !=card.getOwner()) {
                        victim.switchOwner(victim.getOwner());
                    }
                    //处理顽强司命情况下，卡牌已经回到场上，不需要再次结算降临技能。
                    if(victim.isAlive())
                    {
                        return;
                    }
                    //强制移除卡牌，防止新生以后出现卡牌复制。
                    card.getOwner().getHand().removeCard(victim);
                    card.getOwner().getDeck().removeCard(victim);
                    card.getOwner().getField().removeCard(victim);
                    card.getOwner().getOutField().removeCard(victim);
                    defender.getHand().removeCard(victim);
                    defender.getDeck().removeCard(victim);
                    defender.getField().removeCard(victim);
                    defender.getOutField().removeCard(victim);
                    resolver.summonCard(card.getOwner(), victim, null, false, skillUseInfo.getSkill(),0);
                }
            }
        }

    }
}
