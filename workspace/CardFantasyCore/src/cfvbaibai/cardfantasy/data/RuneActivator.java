package cfvbaibai.cardfantasy.data;

public class RuneActivator {
    private RuneActivationType type;
    private int threshold;
    private Race race;
    public RuneActivator(RuneActivationType type, int threshold, Race race) {
        super();
        this.type = type;
        this.threshold = threshold;
        this.race = race;
    }
    public RuneActivationType getType() {
        return type;
    }
    public int getThreshold() {
        return threshold;
    }
    public Race getRace() {
        return race;
    }
    public static RuneActivator heroHP(int threshold) {
        return new RuneActivator(RuneActivationType.HeroHP, threshold, null);
    }
    
}
