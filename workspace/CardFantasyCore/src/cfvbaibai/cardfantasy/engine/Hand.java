package cfvbaibai.cardfantasy.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Card;

public class Hand extends CardPile {
    
    public Hand() {
    }
    
    public void removeCard(CardInfo card) {
        this.getCards().remove(card);
    }
}
