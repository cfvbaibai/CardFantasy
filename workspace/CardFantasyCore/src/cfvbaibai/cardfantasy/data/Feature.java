package cfvbaibai.cardfantasy.data;

public abstract class Feature {

    protected FeatureType type;
    protected int level;
    public Feature(FeatureType type, int level) {
        this.type = type;
        this.level = level;
    }

    public FeatureType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getImpact() {
        return type.getImpact(this.level);
    }

    public String getShortDesc() {
        return String.format("【%s%s】", type.getDisplayName(), level == 0 ? "" : String.valueOf(level));
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isDeathFeature() {
        return false;
    }

    public boolean isSummonFeature() {
        return false;
    }
    
    public static Feature 自动扣血() {
        return new RuneFeature(FeatureType.自动扣血, 0);
    }
}