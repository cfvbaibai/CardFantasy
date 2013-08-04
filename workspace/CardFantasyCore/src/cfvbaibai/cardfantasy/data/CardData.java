package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.NonSerializable;

public class CardData {
    private String id;
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
    private List<CardFeature> cardFeatures;

    public CardData() {
        this.id = "";
        this.name = "";
        this.cardFeatures = new ArrayList<CardFeature>();
        // TODO: Apply real growth
        this.growth = new Growth(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600);
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
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
    
    public List <CardFeature> getFeatures() {
        return this.cardFeatures;
    }
}
