package cfvbaibai.cardfantasy.data;

public class Feature implements Cloneable {
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
        return String.format("¡¾%s%s¡¿", type.getDisplayName(), level == 0 ? "" : String.valueOf(level));
    }
    
    public int getImpact() {
        return type.getImpact(this.level);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
