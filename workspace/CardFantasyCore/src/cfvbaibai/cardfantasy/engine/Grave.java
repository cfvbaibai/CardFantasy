package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class Grave extends CardPile {

    public Grave() {
    }

    public boolean removeCard(CardInfo card) {
        if (!card.isDead()) {
            throw new CardFantasyRuntimeException("Cannot remove undead card: " + card.getShortDesc());
        }
        if (!this.getCards().remove(card)) {
            throw new CardFantasyRuntimeException("Cannot find card in grave: " + card.getShortDesc());
        }
        return true;
    }
    
    @Override
    public CardInfo addCard(CardInfo card) {
        if (card.getOriginalOwner() != null && card.getOriginalOwner() != card.getOwner()) {
            throw new CardFantasyRuntimeException("Cannot add an existing card to grave. "
                    + card.getShortDesc());
        }
        this.getCards().add(0, card);
        return card;
    }

    public CardInfo getFirst() {
        if (this.size() == 0) {
            return null;
        } else {
            return this.getCards().get(0);
        }
    }
    
    public CardInfo pop() {
        CardInfo card = getFirst();
        if (card != null) {
            this.removeCard(card);
        }
        return card;
    }
    
    public List<CardInfo> pop(int count) {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        for (int i = 0; i < count; ++i) {
            CardInfo card = pop();
            if (card == null) {
                break;
            }
            cards.add(card);
        }
        return cards;
    }
}
