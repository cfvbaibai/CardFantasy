package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.NonSerializable;

public enum RuneData {
    荒芜(RuneClass.GROUND, 3, FeatureType.毒液, 3, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(60)),
    沼泽(RuneClass.GROUND, 3, FeatureType.毒雾, 1, 1, 1, Growth.RUNE, RuneActivator.enemyField(2, null)),
    岩晶(RuneClass.GROUND, 3, FeatureType.聚能护甲, 3, 1, 2, Growth.RUNE, RuneActivator.enemyField(1, null)),
    毒砂(RuneClass.GROUND, 3, FeatureType.毒液, 5, 1, 2, Growth.RUNE, RuneActivator.round(12)),
    岩壁(RuneClass.GROUND, 4, FeatureType.格挡, 5, 1, 3, Growth.RUNE, RuneActivator.myField(1, Race.SAVAGE)),
    深渊(RuneClass.GROUND, 4, FeatureType.毒雾, 5, 1, 3, Growth.RUNE, RuneActivator.enemyGrave(1, Race.KINGDOM)),
    石林(RuneClass.GROUND, 4, FeatureType.法力反射, 5, 1, 3, Growth.RUNE, RuneActivator.myGrave(1, Race.SAVAGE)),
    赤谷(RuneClass.GROUND, 5, FeatureType.吸血, 5, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.SAVAGE)),
    陨星(RuneClass.GROUND, 4, FeatureType.瘟疫, 5, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.KINGDOM)),
    飞岩(RuneClass.GROUND, 4, FeatureType.狙击, 5, 1, 5, Growth.RUNE, RuneActivator.myGrave(2, Race.SAVAGE)),
    死域(RuneClass.GROUND, 5, FeatureType.毒云, 6, 1, 5, Growth.RUNE, RuneActivator.myGrave(1, Race.SAVAGE)),
    秽土(RuneClass.GROUND, 4, FeatureType.转生, 4, 1, 5, Growth.RUNE, RuneActivator.myGrave(1, Race.SAVAGE)),
    
    霜冻(RuneClass.WATER, 3, FeatureType.冰弹, 3, 1, 1, Growth.RUNE, RuneActivator.myGrave(2, null)),
    寒潮(RuneClass.WATER, 3, FeatureType.霜冻新星, 1, 1, 1, Growth.RUNE, RuneActivator.myDeck(2, null)),
    冰锥(RuneClass.WATER, 3, FeatureType.冰弹, 5, 1, 2, Growth.RUNE, RuneActivator.round(14)),
    漩涡(RuneClass.WATER, 3, FeatureType.反击, 3, 1, 2, Growth.RUNE, RuneActivator.enemyField(1, Race.HELL)),
    暴雨(RuneClass.WATER, 4, FeatureType.群体削弱, 5, 1, 3, Growth.RUNE, RuneActivator.myGrave(1, Race.KINGDOM)),
    清泉(RuneClass.WATER, 4, FeatureType.甘霖, 5, 1, 3, Growth.RUNE, RuneActivator.myField(1, Race.KINGDOM)),
    怒涛(RuneClass.WATER, 4, FeatureType.狂热, 4, 1, 3, Growth.RUNE, RuneActivator.myHeroHP(40)),
    雪崩(RuneClass.WATER, 4, FeatureType.霜冻新星, 6, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.HELL)),
    冰封(RuneClass.WATER, 3, FeatureType.冰甲, 5, 1, 4, Growth.RUNE, RuneActivator.myGrave(1, Race.KINGDOM)),
    圣泉(RuneClass.WATER, 6, FeatureType.祈祷, 5, 1, 4, Growth.RUNE, RuneActivator.enemyGrave(1, Race.HELL)),
    永冻(RuneClass.WATER, 4, FeatureType.暴风雪, 6, 1, 5, Growth.RUNE, RuneActivator.myField(1, Race.KINGDOM)),
    寒伤(RuneClass.WATER, 3, FeatureType.暴击, 3, 1, 5, Growth.RUNE, RuneActivator.myGrave(3, Race.KINGDOM)),
    
    闪电(RuneClass.WIND, 3, FeatureType.落雷, 3, 1, 1, Growth.RUNE, RuneActivator.enemyGrave(2, null)),
    雷云(RuneClass.WIND, 3, FeatureType.连环闪电, 1, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(50)),
    霹雳(RuneClass.WIND, 3, FeatureType.落雷, 5, 1, 2, Growth.RUNE, RuneActivator.round(14)),
    飞羽(RuneClass.WIND, 4, FeatureType.狙击, 4, 1, 2, Growth.RUNE, RuneActivator.myGrave(1, Race.FOREST)),
    复苏(RuneClass.WIND, 4, FeatureType.回春, 4, 1, 3, Growth.RUNE, RuneActivator.enemyGrave(1, Race.SAVAGE)),
    飓风(RuneClass.WIND, 4, FeatureType.连环闪电, 4, 1, 3, Growth.RUNE, RuneActivator.myHeroHP(40)),
    春风(RuneClass.WIND, 4, FeatureType.群体护甲, 4, 1, 3, Growth.RUNE, RuneActivator.myHand(1, Race.FOREST)),
    洞察(RuneClass.WIND, 4, FeatureType.嗜血, 5, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.SAVAGE)),
    雷盾(RuneClass.WIND, 4, FeatureType.盾刺, 6, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.FOREST)),
    扬旗(RuneClass.WIND, 4, FeatureType.穷追猛打, 6, 1, 4, Growth.RUNE, RuneActivator.myGrave(1, Race.FOREST)),
    雷狱(RuneClass.WIND, 4, FeatureType.雷暴, 6, 1, 5, Growth.RUNE, RuneActivator.myField(1, Race.FOREST)),
    轻灵(RuneClass.WIND, 3, FeatureType.闪避, 5, 1, 5, Growth.RUNE, RuneActivator.myGrave(2, Race.FOREST)),
    
    火拳(RuneClass.FIRE, 3, FeatureType.火球, 3, 1, 1, Growth.RUNE, RuneActivator.enemyField(2, null)),
    热浪(RuneClass.FIRE, 3, FeatureType.火墙, 1, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(60)),
    流火(RuneClass.FIRE, 3, FeatureType.火球, 5, 1, 2, Growth.RUNE, RuneActivator.round(12)),
    红莲(RuneClass.FIRE, 5, FeatureType.治疗, 4, 1, 2, Growth.RUNE, RuneActivator.myHand(1, Race.HELL)),
    冥火(RuneClass.FIRE, 4, FeatureType.烈火焚神, 3, 1, 3, Growth.RUNE, RuneActivator.myGrave(1, Race.HELL)),
    淬炼(RuneClass.FIRE, 4, FeatureType.群攻提升, 4, 1, 3, Growth.RUNE, RuneActivator.myHeroHP(50)),
    焚天(RuneClass.FIRE, 4, FeatureType.火墙, 5, 1, 3, Growth.RUNE, RuneActivator.enemyField(1, Race.FOREST)),
    炎甲(RuneClass.FIRE, 5, FeatureType.魔甲, 4, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.HELL)),
    爆裂(RuneClass.FIRE, 5, FeatureType.自爆, 4, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.FOREST)),
    灼魂(RuneClass.FIRE, 6, FeatureType.天谴, 6, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.HELL)),
    灭世(RuneClass.FIRE, 5, FeatureType.烈焰风暴, 6, 1, 5, Growth.RUNE, RuneActivator.myGrave(1, Race.HELL)),
    绝杀(RuneClass.FIRE, 4, FeatureType.战意, 6, 1, 5, Growth.RUNE, RuneActivator.myGrave(2, Race.HELL)),
    ;

    private RuneClass runeClass;
    @NonSerializable
    private int maxEnergy;
    @NonSerializable
    private FeatureType featureType;
    @NonSerializable
    private int incrFeatureLevel;
    @NonSerializable
    private int initFeatureLevel;
    @NonSerializable
    private Growth growth;
    @NonSerializable
    private int star;
    @NonSerializable
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