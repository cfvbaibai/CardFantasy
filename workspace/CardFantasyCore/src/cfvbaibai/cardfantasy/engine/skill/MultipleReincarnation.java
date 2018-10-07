package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class MultipleReincarnation {
    public static boolean apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, boolean unbending,Player opponent,int rate1,int rate2,int rate3) throws HeroDieSignal {
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
            // 不屈中，此时不发动司命
            return false;
        }
        if(!player.getBeforeDeath().contains(card))
        {
            return false;
        }

        Skill cardSkill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        int reallyRate = resolver.getStage().getRandomizer().next(0,100);
        ui.roll100(reallyRate, rate1+rate2+rate3);
        if(reallyRate>(rate1+rate2+rate3))
        {
            ui.useSkill(card, card, cardSkill, false);
            return false;
        }
        ui.useSkill(card, card, cardSkill, true);
        BeforeDeath beforeDeath = card.getOwner().getBeforeDeath();
        if (rate1>=reallyRate) {
            beforeDeath.removeCard(card);
            //发动成功重置死亡卡牌
            resolver.resetDeadCard(card);
            //card.reset();
            //  player.getField().addCard(card);

            resolver.summonCard(card.getOwner(), card, card, false, cardSkill,0);//司命可以发动降临技能
            CardStatusItem item = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(card, card, cardSkill, item);
            card.addStatus(item);
            return true;
        }
        else if(rate1+rate2>=reallyRate){
            beforeDeath.removeCard(card);
            //发动成功重置死亡卡牌
            resolver.resetDeadCard(card);
            if (player.getDeck().size() > 0) {
                //     int position = Randomizer.getRandomizer().next(0, player.getDeck().size());
                // 回生是有顺序的。
                int position = 0;
                player.getDeck().insertCardToPosition(card, position);
            } else {
                player.getDeck().addCard(card);
            }
            return true;
        }
        else if(rate1+rate2+rate3>=reallyRate){
            beforeDeath.removeCard(card);
            //发动成功重置死亡卡牌
            resolver.resetDeadCard(card);
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
