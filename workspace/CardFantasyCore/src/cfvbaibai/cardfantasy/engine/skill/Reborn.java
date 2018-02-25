package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public final class Reborn {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, boolean unbending) throws HeroDieSignal {
        Player player = card.getOwner();
        if (unbending) {
            return ; // The card is unbending!
        }
        if (card.isSummonedMinion()) {
            return ; // 召唤物不发动司命
        }
        if (!player.getGrave().contains(card) && player.getField().contains(card)) {
            // 不屈中，此时不发动司命
            return ;
        }
        //处理顽强司命情况下，卡牌已经回到场上
        if(card.isAlive())
        {
            return;
        }
        if(!player.getGrave().contains(card))
        {
            //夺魂处理墓地没有卡牌,不发动。
            return;
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
            Grave grave = card.getOwner().getGrave();
            grave.removeCard(card);
          //  player.getField().addCard(card);

            resolver.summonCard(card.getOwner(), card, card, false, cardSkill,1);//司命可以发动降临技能
            CardStatusItem item = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(card, card, cardSkill, item);
            card.addStatus(item);
        }
    }
}
