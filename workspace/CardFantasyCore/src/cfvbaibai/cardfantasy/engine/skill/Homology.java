package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class Homology {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo reviver,String cardName) throws HeroDieSignal {
        if (reviver == null) {
            throw new CardFantasyRuntimeException("reviver should not be null");
        }
        Grave grave = reviver.getOwner().getGrave();
        Hand hand = reviver.getOwner().getHand();
        Deck deck = reviver.getOwner().getDeck();
        List<CardInfo> revivableCards = new ArrayList<CardInfo>();
        for (CardInfo deadCard : grave.toList()) {
            if(deadCard.getName().equals(cardName))
            {
                revivableCards.add(deadCard);
            }
        }
        for (CardInfo handCard : hand.toList()) {
            if(handCard.getName().equals(cardName))
            {
                revivableCards.add(handCard);
            }
        }
        for (CardInfo deckCard : deck.toList()) {
            if(deckCard.getName().equals(cardName))
            {
                revivableCards.add(deckCard);
            }
        }
        if (revivableCards.isEmpty()) {
            return;
        }
        resolver.getStage().getUI().useSkill(reviver, revivableCards, skillUseInfo.getSkill(), true);
        for(CardInfo cardInfo:revivableCards)
        {
            if( reviver.getOwner().getGrave().contains(cardInfo)) {
                reviver.getOwner().getGrave().removeCard(cardInfo);
            }
            reviver.getOwner().getHand().removeCard(cardInfo);
            reviver.getOwner().getDeck().removeCard(cardInfo);
            resolver.summonCard(reviver.getOwner(), cardInfo, reviver, false, skillUseInfo.getSkill(),0);
            CardStatusItem item = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(reviver, cardInfo, skillUseInfo.getSkill(), item);
            cardInfo.addStatus(item);
        }
    }
}
