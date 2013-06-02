package cfvbaibai.cardfantasy.engine;


public class Hand extends CardPile {
    
    public Hand() {
    }
    
    public void removeCard(CardInfo card) {
        this.getCards().remove(card);
    }
}
