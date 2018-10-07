package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public final class Reborn {
    public static boolean apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, boolean unbending) throws HeroDieSignal {
        Player player = card.getOwner();
        if (unbending) {
            return false; // The card is unbending!
        }
        if (card.isSummonedMinion()) {
            return false; // 召唤物不发动司命
        }
//        if (!player.getGrave().contains(card) && player.getField().contains(card)) {
//            // 不屈中，此时不发动司命
//            return false;
//        }
       if (!player.getBeforeDeath().contains(card) && player.getField().contains(card)) {
            // 不屈中，此时不发动司命
            return false;
        }
        //处理顽强司命情况下，卡牌已经回到场上
        if(card.isAlive())
        {
            return false;
        }
//        if(!player.getGrave().contains(card))
//        {
//            //夺魂处理墓地没有卡牌,不发动。
//            return false;
//        }
        if(!player.getBeforeDeath().contains(card))
        {
            //处理死亡前目的没有卡牌,不发动。
            return false;
        }
        int rate;
        Skill cardSkill = skillUseInfo.getSkill();
        if(cardSkill.getType()== SkillType.不灭||cardSkill.getType()== SkillType.顽强)
        {
            rate = cardSkill.getImpact3();
        }
        else{
            rate = cardSkill.getImpact();
        }

        GameUI ui = resolver.getStage().getUI();
        boolean bingo = resolver.getStage().getRandomizer().roll100(rate);
        ui.useSkill(card, card, cardSkill, bingo);
        if(bingo) {
          //  ui.summonCard(player,card);
//            Grave grave = card.getOwner().getGrave();
//            grave.removeCard(card);
            BeforeDeath beforeDeath = card.getOwner().getBeforeDeath();
            if(!beforeDeath.contains(card))
            {
                //某些情况下召唤属性丢失导致卡牌不能找到。
                //  System.out.print("错误转生");
                return false;
            }
            beforeDeath.removeCard(card);
            //发动成功重置死亡卡牌
            resolver.resetDeadCard(card);
          //  player.getField().addCard(card);

            resolver.summonCard(card.getOwner(), card, card, false, cardSkill,0);//司命可以发动降临技能
            CardStatusItem item = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(card, card, cardSkill, item);
            card.addStatus(item);
            return true;
        }
        return false;
    }
}
