package cfvbaibai.cardfantasy.data;

public enum FeatureType {
    ¾Ñ»÷(25),
    Á¬»·ÉÁµç(25),
    ´©´Ì(15),
    Ê¥¹â(15, 15),
    ¸ñµ²(20),
    ÏÝÚå(1),
    ·´»÷(20),
    Ï÷Èõ(10),
    »ðÇò(25),
    ±ùµ¯(20),
    Ä§¼×(140, -10);

    private int initImpact;
    private int incrImpact;
    
    FeatureType(int incrImpact) {
        this(0, incrImpact);
    }
    
    FeatureType(int initImpact, int incrImpact) {
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
    }
    
    public String getDisplayName() {
        return this.name();
    }

    public int getImpact(int level) {
        return initImpact + level * incrImpact;
    }
}
