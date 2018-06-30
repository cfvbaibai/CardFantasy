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
        super.addCard(newCard);
        newCard.resetSummonDelay();
        if(newCard.getAddDelay()>0)
        {
            //解决加入卡组的卡牌也被延迟等待的问题。
            newCard.setSummonDelay(newCard.getSummonDelay() + newCard.getAddDelay());
            newCard.setAddDelay(0);
        }
        return newCard;
    }

    public CardInfo getCard(int i) {
        return this.getCards().get(i);
    }
}
