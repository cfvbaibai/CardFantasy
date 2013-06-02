package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;

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
                throw new CardFantasyRuntimeException("Cannot add an existing card to CardPile. "
                        + newCard.getShortDesc());
            }
        }
        this.cards.add(newCard);
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

    @Override
    public Iterator<CardInfo> iterator() {
        return this.cards.iterator();
    }

}
