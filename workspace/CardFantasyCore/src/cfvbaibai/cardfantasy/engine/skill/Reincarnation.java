package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class Reincarnation {
    public static boolean apply(SkillResolver resolver, Skill cardSkill, CardInfo card, boolean unbending,Player opponent) {
        Player player = card.getOwner();
        if (!card.isDead() || unbending) {
            return false; // The card is unbending!
        }
        if (card.getStatus().containsStatus(CardStatusType.召唤)) {
            // Summoned card cannot be reincarnated.
            return false;
        }
//        if (!player.getGrave().contains(card) && player.getField().contains(card)) {
//            // 不屈中，此时不发动转生
//            return false;
//        }
        if (!player.getBeforeDeath().contains(card) && player.getField().contains(card)) {
            // 不屈中，此时不发动转生
            return false;
        }
        if(!player.getBeforeDeath().contains(card))
        {
            return false;
        }
        int rate = cardSkill.getImpact();

        GameUI ui = resolver.getStage().getUI();
        boolean bingo = resolver.getStage().getRandomizer().roll100(rate);
        ui.useSkill(card, card, cardSkill, bingo);
        if (bingo) {
//            if (!player.getGrave().contains(card) && player.getDeck().contains(card)) {
//                // 特殊情况：兔子死亡复活上来的卡把兔子降临回魂了，导致无法转生，暂时先hack一下
//                if (!player.getDeck().removeCard(card)) {
//                    throw new CardFantasyRuntimeException(
//                        "Cannot find card " + card.getShortDesc() + " in deck of " + player.getId());
//                }
//                player.getGrave().addCard(card);
//            }
//            if(!player.getGrave().contains(card)&& player.getHand().contains(card))
//                {
//                return true;
//            }
//            if (!player.getGrave().contains(card) && player.getField().contains(card)) {
//                // 特殊情况：万兽之王死契召唤月蚀兽把自己复活了无法涅槃，暂时先hack一下
////                if (!player.getField().removeCard(card)) {
////                    throw new CardFantasyRuntimeException(
////                            "Cannot find card " + card.getShortDesc() + " in field of " + player.getId());
////                }
////                player.getGrave().addCard(card);
//                return false;
//            }
//            Grave grave = card.getOwner().getGrave();
//            if(!player.getGrave().contains(card))
//            {
//                //某些情况下召唤属性丢失导致卡牌不能找到。
//              //  System.out.print("错误转生");
//                return false;
//            }
//            grave.removeCard(card);
            BeforeDeath beforeDeath = card.getOwner().getBeforeDeath();
            if(!beforeDeath.contains(card))
            {
                //某些情况下召唤属性丢失导致卡牌不能找到。
                //  System.out.print("错误转生");
                return false;
            }
            beforeDeath.removeCard(card);
            Hand hand = card.getOwner().getHand();
            if (hand.isFull()) {
                ui.cardToDeck(card.getOwner(), card);
                card.getOwner().getDeck().addCard(card);
            } else {
                ui.cardToHand(card.getOwner(), card);
                hand.addCard(card);
            }
            HellPrison.apply(resolver,opponent,player);
            HellPrison.applyCoordination(resolver,opponent);
            return true;
        }
        return false;
    }
}
