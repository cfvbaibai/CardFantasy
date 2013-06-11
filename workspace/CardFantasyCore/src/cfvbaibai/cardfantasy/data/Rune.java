package cfvbaibai.cardfantasy.data;

public class Rune {
    private RuneData data;
    private int exp;
    public Rune(RuneData data, int exp) {
        this.data = data;
        this.exp = exp;
    }
    
    public int getLevel() {
        return data.getGrowth().getLevel(this.exp);
    }
    
    public RuneFeature getFeature() {
        int featureLevel = data.getInitFeatureLevel() + getLevel() * data.getIncrFeatureLevel();
        return new RuneFeature(data.getFeatureType(), featureLevel);
    }
    
    public int getMaxEnergy() {
        return this.data.getMaxEnergy();
    }
    
    public RuneActivator getActivator() {
        return this.data.getActivator();
    }
    
    public RuneClass getRuneClass() {
        return this.data.getRuneClass();
    }

    public String getName() {
        return this.data.name();
    }

    public boolean is(RuneData data) {
        return this.data == data;
    }
}
