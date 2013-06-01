package cfvbaibai.cardfantasy.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Field implements Iterable<CardInfo>{

    private List<CardInfo> cards;
    
    public Field() {
        this.cards = new LinkedList<CardInfo>();
    }

    public void addCard(CardInfo summonedCard) {
        this.cards.add(summonedCard);
    }
    
    @Override
    public Iterator<CardInfo> iterator() {
        return this.cards.iterator();
    }

}
