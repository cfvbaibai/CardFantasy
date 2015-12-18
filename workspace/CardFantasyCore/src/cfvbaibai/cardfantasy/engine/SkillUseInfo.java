package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;

public class SkillUseInfo {

    @NonSerializable
    private EntityInfo owner;
    private Skill skill;
    private SkillUseInfo attachedUseInfo;

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
        if (this.skill.getAttachedSkill() != null) {
            this.attachedUseInfo = new SkillUseInfo(owner, this.skill.getAttachedSkill());
        }
    }
    
    public SkillUseInfo getAttachedUseInfo() {
        return this.attachedUseInfo;
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
    
    @Override
    public int hashCode() {
        return this.owner.hashCode() ^ this.skill.hashCode();
    }
}
