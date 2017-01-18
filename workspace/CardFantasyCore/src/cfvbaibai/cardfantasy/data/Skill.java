package cfvbaibai.cardfantasy.data;

public abstract class Skill implements Comparable<Skill> {

    protected SkillType type;
    protected int level;
    protected AttachedSkill attachedSkill1;
    protected AttachedSkill attachedSkill2;

    public Skill(SkillType type, int level) {
        this.type = type;
        this.level = level;
        SkillType attachedType1 = this.type.getAttachedType1();
        int attachedLevel1 = this.type.getAttachedLevel1();
        if (attachedType1 != null) {
            this.attachedSkill1 = new AttachedSkill(attachedType1, attachedLevel1 == -1 ? level : attachedLevel1, this);            
        }
        SkillType attachedType2 = this.type.getAttachedType2();
        int attachedLevel2 = this.type.getAttachedLevel2();
        if (attachedType2 != null) {
            this.attachedSkill2 = new AttachedSkill(attachedType2, attachedLevel2 == -1 ? level : attachedLevel2, this);            
        }
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

    public int getImpact3() {
        return this.type.getImpact3(this.level);
    }

    public Skill getAttachedSkill1() {
        return this.attachedSkill1;
    }
    
    public Skill getAttachedSkill2() {
        return this.attachedSkill2;
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

    public boolean isPrecastSkill() {
        return false;
    }

    public boolean isPostcastSkill() {
        return false;
    }

    public static Skill 自动扣血() {
        return new RuneSkill(SkillType.自动扣血, 0);
    }
}