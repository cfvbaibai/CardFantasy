package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class CardInitInfo {
    private String id;
    private String name;
    private int at;
    private int hp;
    private int level;
    
    public CardInitInfo(CardInfo card) {
        this.id = card.getId();
        this.name = card.getUniqueName();
        this.at = card.getCurrentAT();
        this.hp = card.getMaxHP();
        this.level = card.getLevel();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAt() {
        return at;
    }
    public void setAt(int at) {
        this.at = at;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
