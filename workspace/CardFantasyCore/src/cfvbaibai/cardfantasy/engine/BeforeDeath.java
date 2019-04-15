package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class BeforeDeath extends CardPile {

    public BeforeDeath() {
    }

    public boolean removeCard(CardInfo card) {
        if (!card.isDead()) {
            throw new CardFantasyRuntimeException("Cannot remove undead card: " + card.getShortDesc());
        }
        if (!this.getCards().remove(card)) {
            throw new CardFantasyRuntimeException("Cannot find card in beforeDeath: " + card.getShortDesc());
        }
        return true;
    }
    
    @Override
//    public CardInfo addCard(CardInfo card,SkillResolver... resolver) {
    public CardInfo addCard(CardInfo card) {
        if (card.getOriginalOwner() != null && card.getOriginalOwner() != card.getOwner()) {
            throw new CardFantasyRuntimeException("Cannot add an existing card to beforeDeath. "
                    + card.getShortDesc());
        }
        this.getCards().add(0, card);
        return card;
    }

    public CardInfo getFirst() {
        if (this.size() == 0) {
            return null;
        } else {
            return this.getCards().get(0);
        }
    }
    
    public CardInfo pop() {
        CardInfo card = getFirst();
        if (card != null) {
            this.removeCard(card);
        }
        return card;
    }
    
    public List<CardInfo> pop(int count) {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        for (int i = 0; i < count; ++i) {
            CardInfo card = pop();
            if (card == null) {
                break;
            }
            cards.add(card);
        }
        return cards;
    }
}
