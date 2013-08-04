package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;


public class Card implements Cloneable {
    private CardData sourceInfo;
    private int exp;
    private String uniqueName;
    private CardFeature extraFeature;
    
    public Card(CardData sourceInfo) {
        this(sourceInfo, 0, "");
    }
    
    public Card(CardData sourceInfo, int cardLevel, String suffix) {
        this(sourceInfo, cardLevel, null, null, suffix);
    }
    
    public Card(CardData sourceInfo, int cardLevel, CardFeature extraFeature, String prefix, String suffix) {
        if (sourceInfo == null) {
            throw new CardFantasyRuntimeException("sourceInfo should not be null");
        }
        if (prefix == null) {
            prefix = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        this.sourceInfo = sourceInfo;
        this.growToLevel(cardLevel);
        this.extraFeature = extraFeature;
        this.uniqueName = prefix + sourceInfo.getName() + suffix;
    }
    
    public String getId() {
        return this.sourceInfo.getId();
    }

    public String getUniqueName() {
        return this.uniqueName;
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

    public List<CardFeature> getAllFeatures() {
        List <CardFeature> features = new ArrayList<CardFeature>(sourceInfo.getFeatures());
        if (this.extraFeature != null) {
            features.add(this.extraFeature);
        }
        return features;
    }

    public void growToLevel(int level) {
        this.exp = sourceInfo.getGrowth().getRequiredExp(level);
    }
    
    public Race getRace() {
        return this.sourceInfo.getRace();
    }

    public String getName() {
        return this.sourceInfo.getName();
    }

    public CardFeature getExtraFeature() {
        return this.extraFeature;
    }

    public int getCost() {
        return this.sourceInfo.getDeckCost();
    }
}
