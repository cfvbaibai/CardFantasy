package cfvbaibai.cardfantasy.data;

public class Feature {
    private FeatureType type;
    private int level;
    private int unlockLevel;

    public Feature(FeatureType type, int level, int unlockLevel) {
        super();
        this.type = type;
        this.level = level;
        this.unlockLevel = unlockLevel;
    }
    
    public FeatureType getType() {
        return type;
    }
    
    public void setType(FeatureType type) {
        this.type = type;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getUnlockLevel() {
        return unlockLevel;
    }
    public void setUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }

    public String getShortDesc() {
        return String.format("%s%d", type.getDisplayName(), level);
    }
}
