package cfvbaibai.cardfantasy.data;

public class Feature implements Cloneable {
    private FeatureType type;
    private int level;
    private int unlockLevel;
    private boolean summonFeature;
    private boolean deathFeature;

    public Feature(FeatureType type, int level, int unlockLevel, boolean summonFeature, boolean deathFeature) {
        super();
        this.type = type;
        this.level = level;
        this.unlockLevel = unlockLevel;
        this.summonFeature = summonFeature;
        this.deathFeature = deathFeature;
    }
    
    public boolean isSummonFeature() {
        return this.summonFeature;
    }
    
    public boolean isDeathFeature() {
        return this.deathFeature;
    }
    
    public FeatureType getType() {
        return type;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getUnlockLevel() {
        return unlockLevel;
    }

    public String getShortDesc() {
        String prefix = "";
        if (this.isSummonFeature()) {
            prefix += "Ωµ¡Ÿ-";
        }
        if (this.isDeathFeature()) {
            prefix += "À¿∆ı-";
        }
        return String.format("°æ%s%s%s°ø", prefix, type.getDisplayName(), level == 0 ? "" : String.valueOf(level));
    }
    
    public int getImpact() {
        return type.getImpact(this.level);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
