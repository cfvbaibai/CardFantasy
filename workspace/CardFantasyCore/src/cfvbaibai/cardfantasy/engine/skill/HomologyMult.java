package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public final class HomologyMult {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo reviver,String... summonedCardsDescs) throws HeroDieSignal {
        if (reviver == null) {
            throw new CardFantasyRuntimeException("reviver should not be null");
        }
        Grave grave = reviver.getOwner().getGrave();
        Hand hand = reviver.getOwner().getHand();
        Deck deck = reviver.getOwner().getDeck();
        List<CardInfo> revivableCards = new ArrayList<CardInfo>();
        List<CardInfo> summonCardCandidates = null;
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(reviver.getOwner());
        for (CardInfo deckCard : deck.toList()) {
            for(CardInfo card : summonCardCandidates)
            {
                if(deckCard.getName().equals(card.getName()))
                {
                    revivableCards.add(deckCard);
                    continue;
                }
            }
        }
        for (CardInfo handCard : hand.toList()) {
            for(CardInfo card : summonCardCandidates)
            {
                if(handCard.getName().equals(card.getName()))
                {
                    revivableCards.add(handCard);
                    continue;
                }
            }
        }
        for (CardInfo graveCard : grave.toList()) {
            for(CardInfo card : summonCardCandidates)
            {
                if(graveCard.getName().equals(card.getName()))
                {
                    revivableCards.add(graveCard);
                    continue;
                }
            }
        }
        if (revivableCards.isEmpty()) {
            return;
        }
        resolver.getStage().getUI().useSkill(reviver, revivableCards, skillUseInfo.getSkill(), true);
        for(CardInfo cardInfo:revivableCards)
        {
            if(cardInfo.isAlive())
            {
                continue;
            }
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
