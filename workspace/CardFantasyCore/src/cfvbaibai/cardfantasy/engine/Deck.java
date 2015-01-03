package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;

public class Deck extends CardPile {

    public Deck(Collection <CardInfo> cards) {
        List <CardInfo> cloned = new ArrayList<CardInfo>(cards);
        Randomizer.getRandomizer().shuffle(cloned);
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
    
    public void removeCard(CardInfo card) {
        if (!this.getCards().remove(card)) {
            throw new CardFantasyRuntimeException("Cannot find card in deck: " + card.getShortDesc());
        }
    }
    
    public void shuffle() {
        Randomizer.getRandomizer().shuffle(this.getCards());
    }
}
