package cfvbaibai.cardfantasy.data;

public class AttachedSkill extends Skill {
	
	private Skill parentSkill;

    public AttachedSkill(SkillType type, int level, Skill parentSkill) {
        super(type, level);
        this.parentSkill = parentSkill;
    }

	@Override
	public String getName() {
		return parentSkill.getName();
	}

	@Override
	public String getShortDesc() {
		return parentSkill.getShortDesc();
	}
	
}
