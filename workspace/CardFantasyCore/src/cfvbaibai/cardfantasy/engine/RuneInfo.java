package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneActivator;
import cfvbaibai.cardfantasy.data.RuneData;

public class RuneInfo extends EntityInfo {
    @NonSerializable
    private Player owner;
    private Rune rune;
    @NonSerializable
    private FeatureInfo featureInfo;
    private int energy;
    private boolean activated;

    public RuneInfo(Rune rune, Player owner) {
        this.rune = rune;
        this.owner = owner;
        this.featureInfo = new FeatureInfo(this, rune.getFeature());
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

    public FeatureInfo getFeatureInfo() {
        return this.featureInfo;
    }
    
    public RuneActivator getActivator() {
        return this.rune.getActivator();
    }

    public int getEnergy() {
        return this.energy;
    }

    public String getShortDesc() {
        String statusText = (this.energy <= 0 && !this.activated) ? "OVER" : (this.activated ? "ON" : "OFF");
        return String.format("¡¾%s%d-%s%d-%d-%s¡¿", this.rune.getName(), this.rune.getLevel(), this.featureInfo.getFeature()
                .getType().name(), this.featureInfo.getFeature().getLevel(), getEnergy(), statusText);
    }

    public boolean is(RuneData data) {
        return this.rune.is(data);
    }

    @Override
    public CardStatus getStatus() {
        return new CardStatus();
    }

    public Feature getFeature() {
        return this.getFeatureInfo().getFeature();
    }

    public String getName() {
        return this.rune.getName();
    }

    public String getRuneClass() {
        return this.rune.getRuneClass().name();
    }
}
