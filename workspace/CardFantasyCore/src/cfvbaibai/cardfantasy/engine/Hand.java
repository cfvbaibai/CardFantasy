package cfvbaibai.cardfantasy.engine;


public class Hand extends CardPile {
    
    private Rule rule;
    public Hand(Rule rule) {
        this.rule = rule;
    }
    
    public boolean isFull() {
        return this.getCards().size() >= rule.getMaxHandCards();
    }
    
    public void removeCard(CardInfo card) {
        this.getCards().remove(card);
    }
    
    @Override
    public CardInfo addCard(CardInfo newCard) {
        super.addCard(newCard);
        newCard.resetSummonDelay();
        return newCard;
    }
}
