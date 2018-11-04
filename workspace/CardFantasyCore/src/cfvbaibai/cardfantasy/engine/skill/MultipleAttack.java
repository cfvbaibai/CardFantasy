package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class MultipleAttack {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker,Player defender, Skill attackSkill,Boolean firstSkill)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackSkill != null) {
            return;
        }
        if (attacker.isDead()) {
            return;
        }
        if(!firstSkill)
        {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int impact2  = skill.getImpact2();
        int damage = attacker.getCurrentAT();
        GameUI ui = resolver.getStage().getUI();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        for(int count=0;count<impact2;count++)
        {
            List<CardInfo> cardList = defender.getField().getAliveCards();
            if(cardList.size()>0) {
                List<CardInfo> victims = random.pickRandom(cardList, 1, true, null);
                for(CardInfo card:victims)
                {
                 //   ui.useSkill(attacker,card,skill,true);
                    resolver.attackCard(attacker, card, skillUseInfo, damage,false);
                }
            }
            else {
               // ui.useSkill(attacker,defender,skill,true);
                resolver.attackHero(attacker, defender, skill, damage);
            }

        }

    }

    public static void applyMultiple(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker,Player defender, Skill attackSkill)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackSkill != null) {
            return;
        }
        if (attacker.isDead()) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int damage = attacker.getCurrentAT();
        GameUI ui = resolver.getStage().getUI();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        int rate = skill.getImpact();
        if(random.roll100(rate)) {
            List<CardInfo> cardList = defender.getField().getAliveCards();
            if (cardList.size() > 0) {
                List<CardInfo> victims = random.pickRandom(cardList, 1, true, null);
                for (CardInfo card : victims) {
                    //   ui.useSkill(attacker,card,skill,true);
                    resolver.attackCard(attacker, card, skillUseInfo, damage, false);
                }
            } else {
                // ui.useSkill(attacker,defender,skill,true);
                resolver.attackHero(attacker, defender, skill, damage);
            }
        }
    }

    //只攻击一下，并且有判定卡牌
    public static void applyFirst(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker,Player defender, Skill attackSkill,Boolean firstSkill)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackSkill != null) {
            return;
        }
        if (attacker.isDead()) {
            return;
        }
        if(!firstSkill)
        {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int damage = attacker.getCurrentAT();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defender.getField().getCardsWithLowestHP(1);
        if(victims.size()>0) {
            for(CardInfo card:victims)
            {
                 ui.useSkill(attacker,card,skill,true);
                resolver.attackCard(attacker, card, skillUseInfo, damage,false);
            }
        }
        else {
            ui.useSkill(attacker,defender,skill,true);
            resolver.attackHero(attacker, defender, skill, damage);
        }

    }
}
