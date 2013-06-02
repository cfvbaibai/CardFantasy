package cfvbaibai.cardfantasy.data;

public enum FeatureType {
    Snipe("¾Ñ»÷", 25),
    ChainLightening("Á¬ËøÉÁµç", 25),
    Penetration("´©´Ì", 15),
    HolyLight("Ê¥¹â", 15, 15),
    Block("¸ñµ²", 20),
    MagicShield("Ä§¼×", 140, -10);
    
    private String displayName;
    private int initImpact;
    private int incrImpact;
   
    FeatureType(String displayName) {
        this(displayName, 0, 0);
    }
    
    FeatureType(String displayName, int incrImpact) {
        this(displayName, 0, incrImpact);
    }
    
    FeatureType(String displayName, int initImpact, int incrImpact) {
        this.displayName = displayName;
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }

    public int getImpact(int level) {
        return initImpact + level * incrImpact;
    }
}
