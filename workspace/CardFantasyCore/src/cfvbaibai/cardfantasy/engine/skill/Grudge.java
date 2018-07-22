package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Grudge {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero,int victimCount) throws HeroDieSignal {

        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), victimCount, true, null);

        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        int effectNumber = skill.getImpact();
        int damage = skill.getImpact2();
        ui.useSkill(attackCard, victims, skill, true);
        CardStatusItem statusItem2 = CardStatusItem.Grudge(damage,skillUseInfo);
        CardStatusItem statusItem1 = CardStatusItem.slience(skillUseInfo);
        statusItem1.setEffectNumber(effectNumber);
        statusItem2.setEffectNumber(effectNumber);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attackCard, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(effectNumber>0)
            {
                if(!victim.getStatus().getStatusOf(CardStatusType.咒怨).isEmpty()){
                    victim.removeForce(CardStatusType.咒怨);
                }
            }
            ui.addCardStatus(attackCard, victim, skill, statusItem2);
            if(!victim.isDeman()) {
                victim.addStatus(statusItem1);
            }
            victim.addStatus(statusItem2);
        }
    }

    public static void Infected(SkillResolver resolver, CardInfo defendCard, Player defenderHero) throws HeroDieSignal {
        if (defendCard == null) {
            return;
        }
        List<CardStatusItem> items = defendCard.getStatus().getStatusOf(CardStatusType.咒怨);
        CardStatusItem item = items.get(0);
        SkillUseInfo skillUseInfo=item.getCause();
        //CardInfo attackCard = skillUseInfo.getOwner();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> excluCards=new ArrayList<>();
        for(CardInfo fieldCard:defenderHero.getField().getAliveCards())
        {
            if(fieldCard.getStatus().containsStatus(CardStatusType.咒怨))
            {
                excluCards.add(fieldCard);
            }
        }
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().getAliveCards(), 1, true, excluCards);

        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        int effectNumber = skill.getImpact();
        int damage = skill.getImpact2();
        ui.useSkill(skillUseInfo.getOwner(), victims, skill, true);
        CardStatusItem statusItem2 = CardStatusItem.Grudge(damage,skillUseInfo);
        CardStatusItem statusItem1 = CardStatusItem.slience(skillUseInfo);
        statusItem1.setEffectNumber(effectNumber);
        statusItem2.setEffectNumber(effectNumber);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(skillUseInfo.getOwner(), victim, skill, 1).isAttackable()) {
                continue;
            }
            if(effectNumber>0)
            {
                if(!victim.getStatus().getStatusOf(CardStatusType.咒怨).isEmpty()){
                    victim.removeForce(CardStatusType.咒怨);
                }
            }
            ui.addCardStatus(skillUseInfo.getOwner(), victim, skill, statusItem2);
            if(!victim.isDeman()) {
                victim.addStatus(statusItem1);
            }
            victim.addStatus(statusItem2);
        }
    }
}
