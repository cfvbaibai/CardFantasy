package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneActivator;
import cfvbaibai.cardfantasy.data.RuneClass;
import cfvbaibai.cardfantasy.data.RuneData;

public class RuneInfo extends EntityInfo {
    @NonSerializable
    private Player owner;
    private Rune rune;
    @NonSerializable
    private SkillUseInfo skillUseInfo;
    private int energy;
    private boolean activated;

    public RuneInfo(Rune rune, Player owner) {
        this.rune = rune;
        this.owner = owner;
        this.skillUseInfo = new SkillUseInfo(this, rune.getSkill());
        this.energy = rune.getMaxEnergy();
        this.activated = false;
    }
    
    public Player getOwner() {
        return this.owner;
    }

    public void activate() {
        if (this.activated) {
            throw new CardFantasyRuntimeException("Rune " + getShortDesc() + " already activated!");
        }
        if (this.energy <= 0) {
            throw new CardFantasyRuntimeException("Cannto activate an empty Rune " + getShortDesc());
        }
        this.activated = true;
        --this.energy;
    }

    public void deactivate() {
        this.activated = false;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public SkillUseInfo getSkillUseInfo() {
        return this.skillUseInfo;
    }
    
    public RuneActivator getActivator() {
        return this.rune.getActivator();
    }

    public int getEnergy() {
        return this.energy;
    }

    public String getShortDesc() {
        String statusText = (this.energy <= 0 && !this.activated) ? "OVER" : (this.activated ? "ON" : "OFF");
        return String.format("【%s%d-%s%d-%d-%s】", this.rune.getName(), this.rune.getLevel(), this.skillUseInfo.getSkill()
                .getType().name(), this.skillUseInfo.getSkill().getLevel(), getEnergy(), statusText);
    }

    public boolean is(RuneData data) {
        return this.rune.is(data);
    }

    @Override
    public CardStatus getStatus() {
        return new CardStatus();
    }

    public Skill getSkill() {
        return this.getSkillUseInfo().getSkill();
    }

    public String getName() {
        return this.rune.getName();
    }

    public RuneClass getRuneClass() {
        return this.rune.getRuneClass();
    }

    public int getMaxEnergy() {
        return this.rune.getMaxEnergy();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
