package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;

public class SkillUseInfo {

    @NonSerializable
    private EntityInfo owner;
    private Skill skill;

    public EntityInfo getOwner() {
        return owner;
    }
    
    public Skill getSkill() {
        return skill;
    }
    
    public SkillType getType() {
        return this.skill.getType();
    }

    public SkillUseInfo(EntityInfo owner, Skill skill) {
        this.skill = skill;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof SkillUseInfo) {
            SkillUseInfo other = (SkillUseInfo)obj;
            return getOwner() == other.getOwner() && getSkill().equals(other.getSkill());
        }
        return false;
    }
}
