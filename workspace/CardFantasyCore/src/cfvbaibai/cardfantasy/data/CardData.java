package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.List;

public class CardData {
    private String id;
    private String name;
    private Race race;
    private int summonSpeed;
    private int star;
    private int baseCost;
    private int baseAT;
    private int baseHP;
    private int incrAT;
    private int incrHP;
    private int incrCost;
    private Growth growth;
    private String wikiId;
    private List<CardSkill> cardSkills;

    public CardData() {
        this.id = "";
        this.wikiId = "";
        this.name = "";
        this.cardSkills = new ArrayList<CardSkill>();
        // TODO: Apply real growth
        this.growth = new Growth(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600);
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getWikiId() {
        return this.wikiId;
    }
    
    public void setWikiId(String wikiId) {
        this.wikiId = wikiId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getSummonSpeed() {
        return summonSpeed;
    }

    public void setSummonSpeed(int summonSpeed) {
        this.summonSpeed = summonSpeed;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(int baseCost) {
        this.baseCost = baseCost;
    }

    public int getBaseAT() {
        return baseAT;
    }

    public void setBaseAT(int baseAT) {
        this.baseAT = baseAT;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getIncrAT() {
        return incrAT;
    }

    public void setIncrAT(int incrAT) {
        this.incrAT = incrAT;
    }

    public int getIncrHP() {
        return incrHP;
    }

    public void setIncrHP(int incrHP) {
        this.incrHP = incrHP;
    }

    public Growth getGrowth() {
        return this.growth;
    }
    
    public int getIncrCost() {
        return this.incrCost;
    }
    
    public void setIncrCost(int incrCost) {
        this.incrCost = incrCost;
    }
    
    public List <CardSkill> getSkills() {
        return this.cardSkills;
    }

    public boolean isMaterial() {
        return this.getIncrAT() == 0 && this.getBaseCost() == 99 && this.getSkills().size() <= 1;
    }
}
