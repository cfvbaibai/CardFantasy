package cfvbaibai.cardfantasy.data;

public abstract class Skill implements Comparable<Skill> {

    protected SkillType type;
    protected int level;
    public Skill(SkillType type, int level) {
        this.type = type;
        this.level = level;
    }

    public SkillType getType() {
        return this.type;
    }
    
    public String getName() {
        return this.getType().name();
    }

    public int getLevel() {
        return this.level;
    }

    public int getImpact() {
        return this.type.getImpact(this.level);
    }
    
    public int getImpact2() {
        return this.type.getImpact2(this.level);
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
    public int compareTo(Skill another) {
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
        if (this.isDeathSkill()) {
            return 2;
        }
        if (this.isSummonSkill()) {
            return 1;
        }
        return 0;
    }

    public boolean isDeathSkill() {
        return false;
    }

    public boolean isSummonSkill() {
        return false;
    }
    
    public static Skill 自动扣血() {
        return new RuneSkill(SkillType.自动扣血, 0);
    }
}