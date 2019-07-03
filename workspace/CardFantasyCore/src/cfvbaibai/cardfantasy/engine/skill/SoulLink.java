package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class SoulLink {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero,int victimCount,int effectNumber) throws HeroDieSignal {

        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), victimCount, true, null);
        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attackCard, victims, skill, true);
        CardStatusItem statusItem = CardStatusItem.soulLink(skillUseInfo);
        statusItem.setEffectNumber(effectNumber);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attackCard, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(effectNumber>0)
            {
                if(!victim.getStatus().getStatusOf(CardStatusType.链接).isEmpty()){
                    victim.removeForce(CardStatusType.链接);
                }
            }
            ui.addCardStatus(attackCard, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
    }

    public static void explode(SkillResolver resolver, CardInfo deadCard) throws HeroDieSignal {
        Player defenderHero  = deadCard.getOwner();
        List<CardInfo> fieldCards = defenderHero.getField().getAliveCards();
        List<CardStatusItem> statusItems = deadCard.getStatus().getStatusOf(CardStatusType.链接);
        CardStatusItem cardStatusItem = statusItems.get(0);
        SkillUseInfo skillUseInfo = cardStatusItem.getCause();
      //  CardInfo attackCard =(CardInfo) skillUseInfo.getOwner();
        List<CardInfo> effectCards = new ArrayList<>();
        for(CardInfo fieldCard:fieldCards)
        {
            if(fieldCard==null)
            {
                continue;
            }
            if(fieldCard.getStatus().containsStatus(CardStatusType.链接))
            {
                fieldCard.removeForce(CardStatusType.链接);
                effectCards.add(fieldCard);
            }
        }
        GameUI ui = resolver.getStage().getUI();
        for(CardInfo effectCard:effectCards)
        {
            ui.useSkill(deadCard, effectCard, skillUseInfo.getSkill(), true);
            resolver.killCard(deadCard,effectCard,skillUseInfo.getSkill());//改为杀死卡进入墓地
        }
    }

}
