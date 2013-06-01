package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Card;

public class CardInfo {
    private Card card;
    private int hp;
    private int at;
    private int summonDelay;
    private CardStatus status;

    public CardInfo(Card card) {
        this.card = card;
        this.hp = card.getMaxHP();
        this.at = card.getInitAT();
        this.summonDelay = card.getSummonSpeed();
        this.status = CardStatus.normal();
    }

    public Card getCard() {
        return this.card;
    }

    public int getAT() {
        return this.at;
    }
    
    public void setAT(int at) {
        this.at = at;
    }
    
    public int getHP() {
        return this.hp;
    }
    
    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getSummonDelay() {
        return this.summonDelay;
    }

    public void setSummonDelay(int summonDelay) {
        this.summonDelay = summonDelay;
    }
    
    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }
}
