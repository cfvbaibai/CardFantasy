package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.Hand;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class Flee {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            int actualDamage) throws HeroDieSignal {
        if (actualDamage <= 0) {
            return;
        }
        if (attacker == null) {
            return;
        }
        if (defender.isDead())
        {
        	return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        Field field = defender.getOwner().getField();
        field.removeCard(defender);
        Hand hand = defender.getOwner().getHand();
        if (hand.isFull()) {
            ui.cardToDeck(defender.getOwner(), defender);
            defender.getOwner().getDeck().addCard(defender);
        } else {
            ui.cardToHand(defender.getOwner(), defender);
            hand.addCard(defender);
        }
    }
}
