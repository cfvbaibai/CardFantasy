package cfvbaibai.cardfantasy.data;

public abstract class Feature implements Comparable<Feature> {

    protected FeatureType type;
    protected int level;
    public Feature(FeatureType type, int level) {
        this.type = type;
        this.level = level;
    }

    public FeatureType getType() {
        return type;
    }
    
    public String getName() {
        return this.getType().name();
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
    
    /**
     * Logic:
     * - 按名字自然顺序
     * - 同名按类别：
     *     普通技能
     *     降临技能
     *     死契技能
     * - 同名同类技能按等级自然顺序
     */
    public int compareTo(Feature another) {
        if (another == null) {
            throw new IllegalArgumentException("another should not be null");
        }
        int result = this.getName().compareToIgnoreCase(another.getName());
        if (result != 0) {
            return result;
        }
        result = this.getSpecialTypeOrder() - another.getSpecialTypeOrder();
        if (result != 0) {
            return result;
        }
        return this.getLevel() - another.getLevel();
    }
    
    private int getSpecialTypeOrder() {
        if (this.isDeathFeature()) {
            return 2;
        }
        if (this.isSummonFeature()) {
            return 1;
        }
        return 0;
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