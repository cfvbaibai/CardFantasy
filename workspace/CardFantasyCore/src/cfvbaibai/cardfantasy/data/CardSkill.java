package cfvbaibai.cardfantasy.data;

public class CardSkill extends Skill implements Cloneable {
    private int unlockLevel;
    protected boolean summonSkill;
    protected boolean deathSkill;
    protected boolean precastSkill;
    protected boolean postcastSkill;
    public CardSkill(SkillType type, int level, int unlockLevel, boolean summonSkill, boolean deathSkill, boolean precastSkill, boolean postcastSkill) {
        super(type, level);
        this.unlockLevel = unlockLevel;
        this.summonSkill = summonSkill;
        this.deathSkill = deathSkill;
        this.precastSkill = precastSkill;
        this.postcastSkill = postcastSkill;
    }

    public int getUnlockLevel() {
        return unlockLevel;
    }

    @Override
    public String getShortDesc() {
        return String.format("【%s】", this.getParsableDesc());
    }

    public String getParsableDesc() {
        String prefix = "";
        if (this.isSummonSkill()) {
            prefix += "降临";
        }
        if (this.isDeathSkill()) {
            prefix += "死契";
        }
        if (this.isPrecastSkill()) {
            prefix += "先机";
        }
        if (this.isPostcastSkill()) {
            prefix += "遗志";
        }
        return String.format("%s%s%s", prefix, type.getDisplayName(), level == 0 ? "" : String.valueOf(level));
    }

    @Override
    public boolean isSummonSkill() {
        return this.summonSkill;
    }

    @Override
    public boolean isDeathSkill() {
        return this.deathSkill;
    }

    @Override
    public boolean isPrecastSkill() {
        return this.precastSkill;
    }

    @Override
    public boolean isPostcastSkill() {
        return this.postcastSkill;
    }
}
