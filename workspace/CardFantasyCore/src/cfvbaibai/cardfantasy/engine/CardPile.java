package cfvbaibai.cardfantasy.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public abstract class CardPile implements Iterable<CardInfo> {

    private List<CardInfo> cards;

    protected CardPile() {
        this.cards = new LinkedList<CardInfo>();
    }
    
    protected List<CardInfo> getCards() {
        return this.cards;
    }
    
    public void addCard(CardInfo newCard) {
        for (CardInfo card : cards) {
            if (card == newCard) {
                throw new CardFantasyRuntimeException("Cannot add an existing card to CardPile. " + newCard.getShortDesc());
            }
        }
        this.cards.add(newCard);
    }
    
    public int size() {
        return this.cards.size();
    }

    @Override
    public Iterator<CardInfo> iterator() {
        return this.cards.iterator();
    }

}
