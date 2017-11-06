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
            // 不屈中，此时不发动转生
            return ;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, card, cardSkill, true);
        ui.summonCard(player,card);
        Grave grave = card.getOwner().getGrave();
        grave.removeCard(card);
        player.getField().addCard(card);
    }
}
