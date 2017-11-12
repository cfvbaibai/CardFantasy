package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Reborn {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo card, boolean unbending) {
        Player player = card.getOwner();
        if (!card.isDead() || unbending) {
            return ; // The card is unbending!
        }
        if (!player.getGrave().contains(card) && player.getField().contains(card)) {
            // 不屈中，此时不发动司命
            return ;
        }
        int rate = cardSkill.getImpact();

        GameUI ui = resolver.getStage().getUI();
        boolean bingo = resolver.getStage().getRandomizer().roll100(rate);
        ui.useSkill(card, card, cardSkill, bingo);
        ui.summonCard(player,card);
        if(bingo) {
            Grave grave = card.getOwner().getGrave();
            grave.removeCard(card);
            player.getField().addCard(card);
        }
    }
}
