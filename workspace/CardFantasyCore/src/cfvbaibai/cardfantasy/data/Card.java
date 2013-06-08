package cfvbaibai.cardfantasy.data;

import java.util.List;


public class Card implements Cloneable {
    private CardData sourceInfo;
    private int exp;
    private String id;
    
    public Card(CardData sourceInfo) {
        this(sourceInfo, 0, "");
    }
    
    public Card(CardData sourceInfo, int cardLevel, String suffix) {
        this.sourceInfo = sourceInfo;
        this.growToLevel(cardLevel);
        this.id = sourceInfo.getName() + suffix;
    }

    public String getId() {
        return this.id;
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

    public List<Feature> getAllFeatures() {
        return sourceInfo.getFeatures();
    }

    public void growToLevel(int level) {
        this.exp = sourceInfo.getGrowth().getRequiredExp(level);
    }
    
    public Race getRace() {
        return this.sourceInfo.getRace();
    }
}
