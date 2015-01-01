package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.NonSerializable;

public enum RuneData {
    荒芜("31395", RuneClass.GROUND, 3, SkillType.毒液, 3, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(60)),
    沼泽("31461", RuneClass.GROUND, 3, SkillType.毒雾, 1, 1, 1, Growth.RUNE, RuneActivator.enemyField(2, null)),
    岩晶("31245", RuneClass.GROUND, 3, SkillType.聚能护甲, 3, 1, 2, Growth.RUNE, RuneActivator.enemyField(1, null)),
    毒砂("31462", RuneClass.GROUND, 3, SkillType.毒液, 5, 1, 2, Growth.RUNE, RuneActivator.round(12)),
    岩壁("31463", RuneClass.GROUND, 4, SkillType.格挡, 5, 1, 3, Growth.RUNE, RuneActivator.myField(1, Race.SAVAGE)),
    深渊("31464", RuneClass.GROUND, 4, SkillType.毒雾, 5, 1, 3, Growth.RUNE, RuneActivator.enemyGrave(1, Race.KINGDOM)),
    石林("31170", RuneClass.GROUND, 4, SkillType.法力反射, 5, 1, 3, Growth.RUNE, RuneActivator.myGrave(1, Race.SAVAGE)),
    赤谷("31355", RuneClass.GROUND, 5, SkillType.吸血, 5, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.SAVAGE)),
    陨星("31465", RuneClass.GROUND, 4, SkillType.瘟疫, 5, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.KINGDOM)),
    飞岩("30802", RuneClass.GROUND, 4, SkillType.狙击, 5, 1, 4, Growth.RUNE, RuneActivator.myGrave(2, Race.SAVAGE)),
    死域("31162", RuneClass.GROUND, 5, SkillType.毒云, 6, 1, 5, Growth.RUNE, RuneActivator.myGrave(1, Race.SAVAGE)),
    秽土("30803", RuneClass.GROUND, 4, SkillType.转生, 4, 1, 5, Growth.RUNE, RuneActivator.myGrave(1, Race.SAVAGE)),
    
    霜冻("31466", RuneClass.WATER, 3, SkillType.冰弹, 3, 1, 1, Growth.RUNE, RuneActivator.myGrave(2, null)),
    寒潮("31400", RuneClass.WATER, 3, SkillType.霜冻新星, 1, 1, 1, Growth.RUNE, RuneActivator.myDeck(2, null)),
    冰锥("31467", RuneClass.WATER, 3, SkillType.冰弹, 5, 1, 2, Growth.RUNE, RuneActivator.round(14)),
    漩涡("31226", RuneClass.WATER, 3, SkillType.反击, 3, 1, 2, Growth.RUNE, RuneActivator.enemyField(1, Race.HELL)),
    暴雨("31468", RuneClass.WATER, 4, SkillType.群体削弱, 5, 1, 3, Growth.RUNE, RuneActivator.myGrave(1, Race.KINGDOM)),
    清泉("31300", RuneClass.WATER, 4, SkillType.甘霖, 5, 1, 3, Growth.RUNE, RuneActivator.myField(1, Race.KINGDOM)),
    怒涛("31469", RuneClass.WATER, 4, SkillType.狂热, 4, 1, 3, Growth.RUNE, RuneActivator.myHeroHP(40)),
    雪崩("31470", RuneClass.WATER, 4, SkillType.霜冻新星, 6, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.HELL)),
    冰封("31471", RuneClass.WATER, 3, SkillType.冰甲, 5, 1, 4, Growth.RUNE, RuneActivator.myGrave(1, Race.KINGDOM)),
    圣泉("30804", RuneClass.WATER, 6, SkillType.祈祷, 5, 1, 4, Growth.RUNE, RuneActivator.enemyGrave(1, Race.HELL)),
    永冻("31164", RuneClass.WATER, 4, SkillType.暴风雪, 6, 1, 5, Growth.RUNE, RuneActivator.myField(1, Race.KINGDOM)),
    寒伤("30805", RuneClass.WATER, 3, SkillType.暴击, 3, 1, 5, Growth.RUNE, RuneActivator.myGrave(3, Race.KINGDOM)),
    
    闪电("31385", RuneClass.WIND, 3, SkillType.落雷, 3, 1, 1, Growth.RUNE, RuneActivator.enemyGrave(2, null)),
    雷云("31472", RuneClass.WIND, 3, SkillType.连环闪电, 1, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(50)),
    霹雳("31397", RuneClass.WIND, 3, SkillType.落雷, 5, 1, 2, Growth.RUNE, RuneActivator.round(14)),
    飞羽("31473", RuneClass.WIND, 4, SkillType.狙击, 4, 1, 2, Growth.RUNE, RuneActivator.myGrave(1, Race.FOREST)),
    复苏("31474", RuneClass.WIND, 4, SkillType.回春, 4, 1, 3, Growth.RUNE, RuneActivator.enemyGrave(1, Race.SAVAGE)),
    飓风("31356", RuneClass.WIND, 4, SkillType.连环闪电, 4, 1, 3, Growth.RUNE, RuneActivator.myHeroHP(40)),
    春风("31357", RuneClass.WIND, 4, SkillType.群体护甲, 4, 1, 3, Growth.RUNE, RuneActivator.myHand(1, Race.FOREST)),
    洞察("31475", RuneClass.WIND, 4, SkillType.嗜血, 5, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.SAVAGE)),
    雷盾("31354", RuneClass.WIND, 4, SkillType.盾刺, 6, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.FOREST)),
    扬旗("30806", RuneClass.WIND, 4, SkillType.穷追猛打, 6, 1, 4, Growth.RUNE, RuneActivator.myGrave(1, Race.FOREST)),
    雷狱("31166", RuneClass.WIND, 4, SkillType.雷暴, 6, 1, 5, Growth.RUNE, RuneActivator.myField(1, Race.FOREST)),
    轻灵("30807", RuneClass.WIND, 3, SkillType.闪避, 5, 1, 5, Growth.RUNE, RuneActivator.myGrave(2, Race.FOREST)),
    
    火拳("31413", RuneClass.FIRE, 3, SkillType.火球, 3, 1, 1, Growth.RUNE, RuneActivator.enemyField(2, null)),
    热浪("31476", RuneClass.FIRE, 3, SkillType.火墙, 1, 1, 1, Growth.RUNE, RuneActivator.myHeroHP(60)),
    流火("31249", RuneClass.FIRE, 3, SkillType.火球, 5, 1, 2, Growth.RUNE, RuneActivator.round(12)),
    红莲("31440", RuneClass.FIRE, 5, SkillType.治疗, 4, 1, 2, Growth.RUNE, RuneActivator.myHand(1, Race.HELL)),
    冥火("31426", RuneClass.FIRE, 4, SkillType.烈火焚神, 3, 1, 3, Growth.RUNE, RuneActivator.myGrave(1, Race.HELL)),
    淬炼("31477", RuneClass.FIRE, 4, SkillType.群攻提升, 4, 1, 3, Growth.RUNE, RuneActivator.myHeroHP(50)),
    焚天("31456", RuneClass.FIRE, 4, SkillType.火墙, 5, 1, 3, Growth.RUNE, RuneActivator.enemyField(1, Race.FOREST)),
    炎甲("31323", RuneClass.FIRE, 5, SkillType.魔甲, 4, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.HELL)),
    爆裂("31321", RuneClass.FIRE, 5, SkillType.自爆, 4, 1, 4, Growth.RUNE, RuneActivator.enemyField(1, Race.FOREST)),
    灼魂("30808", RuneClass.FIRE, 6, SkillType.天谴, 6, 1, 4, Growth.RUNE, RuneActivator.myField(1, Race.HELL)),
    灭世("31168", RuneClass.FIRE, 5, SkillType.烈焰风暴, 6, 1, 5, Growth.RUNE, RuneActivator.myGrave(1, Race.HELL)),
    绝杀("30809", RuneClass.FIRE, 4, SkillType.战意, 6, 1, 5, Growth.RUNE, RuneActivator.myGrave(2, Race.HELL)),
    鬼步("", RuneClass.FIRE, 4, SkillType.群体脱困, 0, 0, 5, Growth.RUNE, RuneActivator.myField(2, Race.HELL))
    ;

    private String wikiId;
    private RuneClass runeClass;
    @NonSerializable
    private int maxEnergy;
    @NonSerializable
    private SkillType skillType;
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

    RuneData(String wikiId, RuneClass runeClass, int maxEnergy, SkillType skillType, int initFeatureLevel,
            int incrFeatureLevel, int star, Growth growth, RuneActivator activator) {
        this.wikiId = wikiId;
        this.runeClass = runeClass;
        this.maxEnergy = maxEnergy;
        this.skillType = skillType;
        this.initFeatureLevel = initFeatureLevel;
        this.incrFeatureLevel = incrFeatureLevel;
        this.growth = growth;
        this.star = star;
        this.activator = activator;
    }
    
    public String getWikiId() {
        return wikiId;
    }

    public RuneClass getRuneClass() {
        return runeClass;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public SkillType getFeatureType() {
        return skillType;
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