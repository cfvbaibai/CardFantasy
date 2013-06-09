package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;


public class Grave extends CardPile {

    public Grave() {
    }

    public void removeCard(CardInfo card) {
        if (!card.isDead()) {
            throw new CardFantasyRuntimeException("Cannot remove undead card: " + card.getShortDesc(true));
        }
        if (!this.getCards().remove(card)) {
            throw new CardFantasyRuntimeException("Cannot find card in grave: " + card.getShortDesc(true));
        }
    }

}
