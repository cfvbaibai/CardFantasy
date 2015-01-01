package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class Card implements Cloneable, Comparable <Card> {
    private CardData sourceInfo;
    private int exp;
    private String uniqueName;
    private CardSkill extraFeature;

    public Card(CardData sourceInfo) {
        this(sourceInfo, 0, "");
    }

    public Card(CardData sourceInfo, int cardLevel, String suffix) {
        this(sourceInfo, cardLevel, null, null, suffix);
    }

    public Card(CardData sourceInfo, int cardLevel, CardSkill extraFeature, String prefix, String suffix) {
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
    
    public String getWikiId() {
        return this.sourceInfo.getWikiId();
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

    public List<CardSkill> getAllFeatures() {
        List <CardSkill> features = new ArrayList<CardSkill>(sourceInfo.getFeatures());
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

    public CardSkill getExtraFeature() {
        return this.extraFeature;
    }

    public int getCost() {
        int cost = this.sourceInfo.getBaseCost();
        if (this.getLevel() > 10 || this.extraFeature != null) {
            cost += this.sourceInfo.getIncrCost();
        }
        return cost;
    }

    public int getStar() {
        return this.sourceInfo.getStar();
    }

    @Override
    public int compareTo(Card another) {
        int result = this.getName().compareToIgnoreCase(another.getName());
        if (result != 0) {
            return result;
        }
        result = this.getLevel() - another.getLevel();
        if (result != 0) {
            return result;
        }
        if (this.getExtraFeature() == null && another.getExtraFeature() == null) {
            return 0;
        }
        if (this.getExtraFeature() == null) {
            return -1;
        }
        if (another.getExtraFeature() == null) {
            return 1;
        }
        return this.getExtraFeature().compareTo(another.getExtraFeature());
    }
}
