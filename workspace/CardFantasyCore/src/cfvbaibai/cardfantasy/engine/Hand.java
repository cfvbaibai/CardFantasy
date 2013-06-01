package cfvbaibai.cardfantasy.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Card;

public class Hand implements Iterable <CardInfo> {
    private List <CardInfo> cards;
    
    public Hand() {
        this.cards = new LinkedList<CardInfo>();
    }
    
    public void addCard(CardInfo card) {
        this.cards.add(card);
    }
    
    public void removeCard(CardInfo card) {
        this.cards.remove(card);
    }
    
    public int size() {
        return this.cards.size();
    }

    @Override
    public Iterator<CardInfo> iterator() {
        return this.cards.iterator();
    }
}
