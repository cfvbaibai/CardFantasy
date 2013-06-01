package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Card;

public class Deck implements Iterable<CardInfo> {

    private List<CardInfo> cards;

    public Deck(Collection <CardInfo> cards) {
        List <CardInfo> cloned = new ArrayList<CardInfo>(cards);
        Collections.shuffle(cloned, Randomizer.getRandom());
        this.cards = new LinkedList<CardInfo>(cloned);
    }
    
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    public CardInfo draw() {
        if (this.cards.isEmpty()) {
            return null;
        }
        return this.cards.remove(0);
    }

    @Override
    public Iterator<CardInfo> iterator() {
        return this.cards.iterator();
    }
}
