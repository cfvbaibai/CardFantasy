package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Reborn {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo card, boolean unbending) {
        Player player = card.getOwner();
        if (unbending) {
            return ; // The card is unbending!
        }
        if (!player.getGrave().contains(card) && player.getField().contains(card)) {
            // 不屈中，此时不发动司命
            return ;
        }
        if(!player.getGrave().contains(card))
        {
            //夺魂处理墓地没有卡牌,不发动。
            return;
        }
        int rate;
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
            ui.summonCard(player,card);
            Grave grave = card.getOwner().getGrave();
            grave.removeCard(card);
            player.getField().addCard(card);
        }
    }
}
