package cfvbaibai.cardfantasy.web.animation;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class CardRuntimeInfo extends CardInitInfo {

    private int originalMaxHP;
    private int originalAT;
    
    public CardRuntimeInfo(CardInfo card) {
        super(card);
        this.originalAT = card.getInitAT();
        this.originalMaxHP = card.getRawMaxHP();
    }

    public int getOriginalMaxHP() {
        return this.originalMaxHP;
    }
    
    public int getOriginalAT() {
        return this.originalAT;
    }
}
