package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;

public class Deck extends CardPile {

    public Deck(Collection <CardInfo> cards) {
        this.getCards().addAll(cards);
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

    public void shuffle() {
        Randomizer.getRandomizer().shuffle(this.getCards());
    }
}
