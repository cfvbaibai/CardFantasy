package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class Card implements Cloneable, Comparable <Card> {
    private CardData sourceInfo;
    private int exp;
    private String uniqueName;
    private CardSkill extraSkill;
    private int overrideHP;

    public Card(CardData sourceInfo) {
        this(sourceInfo, 0, "");
    }

    public Card(CardData sourceInfo, int cardLevel, String suffix) {
        this(sourceInfo, cardLevel, null, null, suffix);
    }

    public Card(CardData sourceInfo, int cardLevel, CardSkill extraSkill, String prefix, String suffix) {
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
        this.extraSkill = extraSkill;
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
        if (this.overrideHP <= 0) {
            return this.sourceInfo.getBaseHP() + this.sourceInfo.getIncrHP() * this.getLevel();
        } else {
            return this.overrideHP;
        }
    }
    
    public void setOverrideHP(int overrideHP) {
        this.overrideHP = overrideHP;
    }

    public List<CardSkill> getAllSkills() {
        List <CardSkill> skills = new ArrayList<CardSkill>(sourceInfo.getSkills());
        if (this.extraSkill != null) {
            skills.add(this.extraSkill);
        }
        return skills;
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

    public CardSkill getExtraSkill() {
        return this.extraSkill;
    }

    public int getCost() {
        int cost = this.sourceInfo.getBaseCost();
        if (this.getLevel() > 10 || this.extraSkill != null) {
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
        if (this.getExtraSkill() == null && another.getExtraSkill() == null) {
            return 0;
        }
        if (this.getExtraSkill() == null) {
            return -1;
        }
        if (another.getExtraSkill() == null) {
            return 1;
        }
        return this.getExtraSkill().compareTo(another.getExtraSkill());
    }
    
    public String getParsableDesc() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getName());
        if (this.getExtraSkill() != null) {
            sb.append("+");
            sb.append(this.getExtraSkill().getParsableDesc());
        }
        sb.append("-");
        sb.append(this.getLevel());
        return sb.toString();
    }
}
