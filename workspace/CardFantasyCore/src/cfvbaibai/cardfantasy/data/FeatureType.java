package cfvbaibai.cardfantasy.data;

public enum FeatureType {
    Snipe("¾Ñ»÷"),
    ChainLightening("Á¬ËøÉÁµç"),
    Penetration("´©´Ì"),
    HolyLight("Ê¥¹â"),
    Block("¸ñµ²");
    
    private String displayName;
    FeatureType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
}
