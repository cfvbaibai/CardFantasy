package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.RuneInfo;

public class RuneInitInfo {
    private String ownerId;
    private String name;
    private String type;
    private int energy;
    
    public RuneInitInfo(RuneInfo rune) {
        this.name = rune.getName();
        this.type = rune.getRuneClass();
        this.energy = rune.getEnergy();
        this.ownerId = rune.getOwner().getId();
    }
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
    public int getEnergy() {
        return this.energy;
    }
    public String getOwnerId() {
        return this.ownerId;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
