package cfvbaibai.cardfantasy.data;


public class Card implements Cloneable {
    private CardData sourceInfo;
    private Adjustments adjustments;
    private int exp;
    
    private Card() {
    }
    
    public Card(CardData sourceInfo) {
        this.sourceInfo = sourceInfo;
        this.adjustments = new Adjustments();
        this.exp = 0;
    }
    
    public Card clone() {
        Card card = new Card();
        card.sourceInfo = this.sourceInfo;
        card.exp = this.exp;
        card.adjustments = new Adjustments(this.adjustments);
        return card;
    }
    
    public String getName() {
        return this.sourceInfo.getName();
    }
    
    public int getSummonSpeed() {
        return this.sourceInfo.getSummonSpeed();
    }
    
    public int getLevel() {
        return sourceInfo.getGrowth().getLevel(exp);
    }
    
    public int getInitAT() {
        return this.sourceInfo.getBaseAT() + this.sourceInfo.getIncrAT() * this.getLevel();
    }
    
    public int getMaxHP() {
        return this.sourceInfo.getBaseHP() + this.sourceInfo.getIncrHP() * this.getLevel();
    }
}
