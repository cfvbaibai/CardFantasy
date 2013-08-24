package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.RuneInfo;

public class RuneInitInfo {
    private String name;
    private String type;
    private int energy;
    
    public RuneInitInfo(RuneInfo rune) {
        this.name = rune.getName();
        this.type = rune.getRuneClass();
        this.energy = rune.getEnergy();
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
    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
