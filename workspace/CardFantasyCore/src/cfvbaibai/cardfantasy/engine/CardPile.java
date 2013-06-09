package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public abstract class CardPile {

    private List<CardInfo> cards;

    protected CardPile() {
        this.cards = new LinkedList<CardInfo>();
    }

    protected List<CardInfo> getCards() {
        return this.cards;
    }
    
    public List<CardInfo> toList() {
        return new ArrayList<CardInfo>(this.getCards());
    }

    public void addCard(CardInfo newCard) {
        checkCardExistence(newCard);
        this.cards.add(newCard);
    }
    
    public void insertCard(CardInfo newCard, int index) {
        checkCardExistence(newCard);
        this.cards.add(index, newCard);
    }

    private void checkCardExistence(CardInfo newCard) {
        if (cards.contains(newCard)) {
            throw new CardFantasyRuntimeException("Cannot add an existing card to CardPile. "
                    + newCard.getShortDesc(true));
        }
    }

    public int size() {
        return this.cards.size();
    }

    public List<CardInfo> pickRandom(int max, boolean noEmptyPosition) {
        if (max == 0) {
            return new ArrayList<CardInfo>();
        }
        List<CardInfo> clone = new ArrayList<CardInfo>(cards);
        Collections.shuffle(clone);
        List<CardInfo> result = new LinkedList<CardInfo>();
        for (CardInfo card : clone) {
            if (card == null && noEmptyPosition) {
                continue;
            }
            result.add(card);
            if (result.size() == max) {
                break;
            }
        }
        return result;
    }
}
