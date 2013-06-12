package cfvbaibai.cardfantasy.data;

public enum RuneData {
    »ÄÎß(RuneClass.GROUND, 3, FeatureType.¶¾Òº, 3, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(60)),
    ÕÓÔó(RuneClass.GROUND, 3, FeatureType.¶¾Îí, 1, 1, 1, Growth.RUNE, RuneActivator.enemyField(2, null)),
    ÑÒ¾§(RuneClass.GROUND, 3, FeatureType.¾ÛÄÜ»¤¼×, 3, 1, 2, Growth.RUNE, RuneActivator.enemyField(1, null)),
    ¶¾É°(RuneClass.GROUND, 3, FeatureType.¶¾Òº, 5, 1, 2, Growth.RUNE, RuneActivator.round(12)),
    ÑÒ±Ú(RuneClass.GROUND, 4, FeatureType.¸ñµ², 5, 1, 3, Growth.RUNE, RuneActivator.myField(1, Race.Âù»Ä)),
    ;
    
    private RuneClass runeClass;
    private int maxEnergy;
    private FeatureType featureType;
    private int incrFeatureLevel;
    private int initFeatureLevel;
    private Growth growth;
    private int star;
    private RuneActivator activator;

    RuneData(RuneClass runeClass, int maxEnergy, FeatureType featureType, int initFeatureLevel,
            int incrFeatureLevel, int star, Growth growth, RuneActivator activator) {
        this.runeClass = runeClass;
        this.maxEnergy = maxEnergy;
        this.featureType = featureType;
        this.initFeatureLevel = initFeatureLevel;
        this.incrFeatureLevel = incrFeatureLevel;
        this.growth = growth;
        this.star = star;
        this.activator = activator;
    }

    public RuneClass getRuneClass() {
        return runeClass;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public int getIncrFeatureLevel() {
        return incrFeatureLevel;
    }

    public int getInitFeatureLevel() {
        return initFeatureLevel;
    }

    public Growth getGrowth() {
        return growth;
    }

    public int getStar() {
        return star;
    }
    
    public RuneActivator getActivator() {
        return this.activator;
    }
}
