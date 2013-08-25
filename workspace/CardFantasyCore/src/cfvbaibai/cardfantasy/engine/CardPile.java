package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private void checkCardExistence(CardInfo newCard) {
        if (cards.contains(newCard)) {
            throw new CardFantasyRuntimeException("Cannot add an existing card to CardPile. "
                    + newCard.getShortDesc());
        }
    }

    public int size() {
        return this.cards.size();
    }

    public List<CardInfo> pickRandom(int max, boolean noEmptyPosition, CardInfo exclusion) {
        if (max == 0) {
            return new ArrayList<CardInfo>();
        }
        if (max < 0) {
            max = cards.size();
        }
        final List<CardInfo> clone = new ArrayList<CardInfo>(cards);
        Collections.shuffle(clone);
        List<CardInfo> result = new LinkedList<CardInfo>();
        for (CardInfo card : clone) {
            if (card == null && noEmptyPosition) {
                continue;
            }
            if (card == exclusion) {
                continue;
            }
            result.add(card);
            if (result.size() == max) {
                break;
            }
        }
        Collections.sort(result, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                return clone.indexOf(o1) - clone.indexOf(o2);
            }
        });
        return result;
    }
    
    public List<CardInfo> pickRandom(int max, boolean noEmptyPosition) {
        return pickRandom(max, noEmptyPosition, null);
    }
}
