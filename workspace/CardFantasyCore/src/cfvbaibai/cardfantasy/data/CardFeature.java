package cfvbaibai.cardfantasy.data;

public class CardFeature extends Skill implements Cloneable {
    private int unlockLevel;
    protected boolean summonFeature;
    protected boolean deathFeature;
    public CardFeature(FeatureType type, int level, int unlockLevel, boolean summonFeature, boolean deathFeature) {
        super(type, level);
        this.unlockLevel = unlockLevel;
        this.summonFeature = summonFeature;
        this.deathFeature = deathFeature;
    }
    
    public int getUnlockLevel() {
        return unlockLevel;
    }

    @Override
    public String getShortDesc() {
        String prefix = "";
        if (this.isSummonFeature()) {
            prefix += "降临-";
        }
        if (this.isDeathFeature()) {
            prefix += "死契-";
        }
        return String.format("【%s%s%s】", prefix, type.getDisplayName(), level == 0 ? "" : String.valueOf(level));
    }
    
    @Override
    public boolean isSummonFeature() {
        return this.summonFeature;
    }

    @Override
    public boolean isDeathFeature() {
        return this.deathFeature;
    }
}
