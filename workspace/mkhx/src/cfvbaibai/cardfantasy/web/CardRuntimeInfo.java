package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class CardRuntimeInfo extends CardInitInfo {

    private int originalMaxHP;
    private int originalAT;
    
    public CardRuntimeInfo(CardInfo card) {
        super(card);
        this.originalAT = card.getOriginalAT();
        this.originalMaxHP = card.getOriginalMaxHP();
    }

    public int getOriginalMaxHP() {
        return this.originalMaxHP;
    }
    
    public int getOriginalAT() {
        return this.originalAT;
    }
}
