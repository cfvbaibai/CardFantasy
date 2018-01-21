package cfvbaibai.cardfantasy.engine;


import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class Hand extends CardPile {
    
    private Rule rule;
    public Hand(Rule rule) {
        this.rule = rule;
    }
    
    public boolean isFull() {
        return this.getCards().size() >= rule.getMaxHandCards();
    }

    @Override
    public CardInfo addCard(CardInfo newCard) {
        newCard.restoreOwner();
        super.addCard(newCard);
        newCard.resetSummonDelay();
        return newCard;
    }

    public CardInfo getCard(int i) {
        return this.getCards().get(i);
    }
}
