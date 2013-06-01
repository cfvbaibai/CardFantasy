package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class Field extends CardPile {

    public Field() {
    }

    public CardInfo getCard(int i) {
        if (i < 0) {
            throw new CardFantasyRuntimeException("Invalid parameter i: " + i + ". i must be a positive integer.");
        }
        if (i >= size()) {
            return null;
        } else {
            return this.getCards().get(i);
        }
    }

    public void expelCard(int i) {
        if (i < 0 || i >= getCards().size()) {
            throw new CardFantasyRuntimeException("Invalid parameter i: " + i + ". Card count = " + size());
        }
        getCards().set(i, null);
    }

    public void compact() {
        int i = 0;
        while (i < size()) {
            if (getCards().get(i) == null) {
                getCards().remove(i);
            } else {
                ++i;
            }
        }
    }
}
