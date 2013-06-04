package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;

public class Deck extends CardPile {

    public Deck(Collection <CardInfo> cards) {
        List <CardInfo> cloned = new ArrayList<CardInfo>(cards);
        Randomizer.shuffle(cloned);
        this.getCards().addAll(cloned);
    }
    
    public boolean isEmpty() {
        return this.getCards().isEmpty();
    }

    public CardInfo draw() {
        if (this.getCards().isEmpty()) {
            return null;
        }
        return this.getCards().remove(0);
    }
}
