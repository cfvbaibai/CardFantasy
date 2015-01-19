package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class CardPile {

    private List<CardInfo> cards;

    public CardPile() {
        this.cards = new LinkedList<CardInfo>();
    }

    protected List<CardInfo> getCards() {
        return this.cards;
    }
    
    public List<CardInfo> toList() {
        return new ArrayList<CardInfo>(this.getCards());
    }

    public CardInfo addCard(CardInfo newCard) {
        checkCardExistence(newCard);
        this.cards.add(newCard);
        return newCard;
    }

    public boolean contains(CardInfo card) {
        return this.cards.contains(card);
    }

    public void insertCardToPosition(CardInfo newCard, int position) {
        checkCardExistence(newCard);
        if (position >= this.size())
        {
            this.cards.add(newCard);
        }
        else
        {
            this.cards.add(position, newCard);
        }
    }

    private void checkCardExistence(CardInfo newCard) {
        if (cards.contains(newCard)) {
            throw new CardFantasyRuntimeException("Cannot add an existing card to CardPile. "
                    + newCard.getShortDesc());
        }
    }
    
    public void clear() {
        this.cards.clear();
    }

    public void removeCard(CardInfo card) {
        this.cards.remove(card);
    }

    public int size() {
        return this.cards.size();
    }
}
