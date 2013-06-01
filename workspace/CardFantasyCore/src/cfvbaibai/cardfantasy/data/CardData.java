package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.List;

public class CardData {
    private int id;
    private String name;
    private Race race;
    private int summonSpeed;
    private int star;
    private int deckCost;
    private int baseAT;
    private int baseHP;
    private int incrAT;
    private int incrHP;
    private Growth growth;
    private List<Feature> features;

    public CardData() {
        this.name = "";
        this.features = new ArrayList<Feature>();
        // TODO: Apply real growth
        this.growth = new Growth(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
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

    public int getDeckCost() {
        return deckCost;
    }

    public void setDeckCost(int deckCost) {
        this.deckCost = deckCost;
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
    
    public List <Feature> getFeatures() {
        return this.features;
    }
}
