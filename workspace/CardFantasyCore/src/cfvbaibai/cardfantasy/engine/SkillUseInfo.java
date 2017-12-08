package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;

public class SkillUseInfo {

    @NonSerializable
    private EntityInfo owner;
    private Skill skill;    
    private SkillUseInfo attachedUseInfo1;
    private SkillUseInfo attachedUseInfo2;
    private  int giveSkill;//判断是否是附加技能
    private  int skillNumber;//限定技能可以释放的次数


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
        this.giveSkill = 0;
        this.skillNumber=-1;
        if (this.skill.getAttachedSkill1() != null) {
            this.attachedUseInfo1 = new SkillUseInfo(owner, this.skill.getAttachedSkill1());
        }
        if (this.skill.getAttachedSkill2() != null) {
            this.attachedUseInfo2 = new SkillUseInfo(owner, this.skill.getAttachedSkill2());
        }
    }
    
    public SkillUseInfo getAttachedUseInfo1() {
        return this.attachedUseInfo1;
    }
    
    public SkillUseInfo getAttachedUseInfo2() {
        return this.attachedUseInfo2;
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

    public int getSkillNumber() {
        return skillNumber;
    }

    public void setSkillNumber(int skillNumber) {
        this.skillNumber = skillNumber;
    }

    public int getGiveSkill(){
        return this.giveSkill;
    }

    public  void setGiveSkill(int giveSkill){
        this.giveSkill =giveSkill;
    }
}
