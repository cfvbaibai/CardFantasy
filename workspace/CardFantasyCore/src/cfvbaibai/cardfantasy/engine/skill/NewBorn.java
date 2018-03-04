package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;

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
        List<CardInfo> victims = random.pickRandom(frontCards , victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();

        for (CardInfo victim : victims) {
            if (!victim.containsAllUsableSkillsWithTag(SkillTag.新生)) {
                for (SkillUseInfo victimSkillUseInfo : victim.getAllUsableSkillsIgnoreSilence()) {
                    resolver.getStage().removeUsed(victimSkillUseInfo,skillUseInfo.getOwner().getOwner(),defender);
                }
                ui.useSkill(card, victim, skillUseInfo.getSkill(), true);
                resolver.getStage().getUI().useSkill(card, victim, skillUseInfo.getSkill(), true);
                if (victim.getStatus().containsStatus(CardStatusType.召唤)) {
                    resolver.killCard(card,victim,skill);//改为杀死卡进入墓地
                 //   resolver.resolveDeathSkills(card,victim,skillUseInfo.getSkill(),result);//新生可以发动死契
                    Summon.apply(resolver, skillUseInfo, card, SummonType.Summoning, 1, victim.getName());//新生卡牌可以立即攻击。
                }
                else{
                    resolver.killCard(card,victim,skill);//改为杀死卡进入墓地
                //    resolver.resolveDeathSkills(card,victim,skillUseInfo.getSkill(),result);//新生可以发动死契
                    if(victim.getOwner() !=card.getOwner()) {
                        victim.switchOwner(victim.getOwner());
                    }
                    //处理顽强司命情况下，卡牌已经回到场上，不需要再次结算死契
                    if(victim.isAlive())
                    {
                        return;
                    }
                    resolver.summonCard(card.getOwner(), victim, null, false, skillUseInfo.getSkill());
                }
            }
        }

    }
}
