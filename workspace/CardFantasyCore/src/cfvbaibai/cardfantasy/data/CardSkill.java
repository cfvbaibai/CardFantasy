package cfvbaibai.cardfantasy.data;

public class CardSkill extends Skill implements Cloneable {
    private int unlockLevel;
    protected boolean summonSkill;
    protected boolean deathSkill;
    public CardSkill(SkillType type, int level, int unlockLevel, boolean summonSkill, boolean deathSkill) {
        super(type, level);
        this.unlockLevel = unlockLevel;
        this.summonSkill = summonSkill;
        this.deathSkill = deathSkill;
    }
    
    public int getUnlockLevel() {
        return unlockLevel;
    }

    @Override
    public String getShortDesc() {
        String prefix = "";
        if (this.isSummonSkill()) {
            prefix += "降临-";
        }
        if (this.isDeathSkill()) {
            prefix += "死契-";
        }
        return String.format("【%s%s%s】", prefix, type.getDisplayName(), level == 0 ? "" : String.valueOf(level));
    }
    
    @Override
    public boolean isSummonSkill() {
        return this.summonSkill;
    }

    @Override
    public boolean isDeathSkill() {
        return this.deathSkill;
    }
}
