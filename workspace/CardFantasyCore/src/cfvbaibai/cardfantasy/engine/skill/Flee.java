package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Hand;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;

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
        if (defender.getOwner().getHand().contains(defender) || defender.getOwner().getDeck().contains(defender)) {
            // 如果已经转生了，那么就不再发动逃跑了
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        // 如果没被物理攻击直接秒杀，需要从场上把卡牌去除
        // defender.getOwner().getField().removeCard(defender); BUG! 不能用removeCard，那样会使场上的卡重排，战斗中不该重排
        defender.getOwner().getField().expelCard(defender.getPosition());
        
        // 如果是被召唤的卡牌，发动逃跑技能后应该直接消失
        if (defender.isSummonedMinion()) {
            return;
        }

        Hand hand = defender.getOwner().getHand();
        if (hand.isFull()) {
            ui.cardToDeck(defender.getOwner(), defender);
            defender.getOwner().getDeck().addCard(defender);
        } else {
            ui.returnCard(attacker, defender, cardSkill);
            ui.cardToHand(defender.getOwner(), defender);
            hand.addCard(defender);
        }
    }
}
