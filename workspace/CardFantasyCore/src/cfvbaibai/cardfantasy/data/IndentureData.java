package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.NonSerializable;

public enum IndentureData {
    王国的护卫("", 10, 1, IndentureActivator.myField(10,  Race.KINGDOM)),
    森林的盟友("", 10, 1, IndentureActivator.myField(10,  Race.FOREST)),
    蛮荒的援军("", 10, 1, IndentureActivator.myField(10,  Race.SAVAGE)),
    地狱的爪牙("", 10, 1, IndentureActivator.myField(10,  Race.HELL)),
    恶魔的仆从("", 10, 1, IndentureActivator.myField(10,  Race.DEMON)),

    天使的护佑("", 0, -10, IndentureActivator.myHeroHPLess(0)),
    生命召唤("", 105, 10, IndentureActivator.myHeroHPMore(105)),

    王国的革命("", 10, 1, IndentureActivator.enemyField(10,  Race.KINGDOM)),
    森林的毁灭("", 10, 1, IndentureActivator.enemyField(10,  Race.FOREST)),
    蛮荒的挑战("", 10, 1, IndentureActivator.enemyField(10,  Race.SAVAGE)),
    地狱的净化("", 10, 1, IndentureActivator.enemyField(10,  Race.HELL)),
    恶魔的征讨者("", 10, 1, IndentureActivator.enemyField(10,  Race.DEMON)),

    先锋("", 0, -4, IndentureActivator.roundLess(0)),
    后援("", 44, 4, IndentureActivator.roundMore(44)),

    背水一战("", 0, 0, IndentureActivator.fieldDiff(0)),

    王国的旗帜("", 5, 1, IndentureActivator.myHand(5, Race.KINGDOM)),
    森林的号角("", 5, 1, IndentureActivator.myHand(5, Race.FOREST)),
    蛮荒的战鼓("", 5, 1, IndentureActivator.myHand(5, Race.SAVAGE)),
    地狱的召唤("", 5, 1, IndentureActivator.myHand(5, Race.HELL)),

    王国的惩戒("", 10, 1, IndentureActivator.myGrave(10, Race.KINGDOM)),
    森林的复仇("", 10, 1, IndentureActivator.myGrave(10, Race.FOREST)),
    蛮荒的反攻("", 10, 1, IndentureActivator.myGrave(10, Race.SAVAGE)),
    地狱的复仇("", 10, 1, IndentureActivator.myGrave(10, Race.HELL)),
    预备队("", 10, 1, IndentureActivator.myGrave(10, null)),

    正义联军("", 5, 1, IndentureActivator.doubleOrRace(5)),
    正义的伙伴("", 5, 1, IndentureActivator.doubleRace(5)),
    四族联军("", 5, 0, IndentureActivator.fourRace(5)),
    ;

    private String wikiId;
    @NonSerializable
    private int incrLevel;
    @NonSerializable
    private int initLevel;
    @NonSerializable
    private IndentureActivator indentureActivator;

    IndentureData(String wikiId, int initLevel,
                  int incrLevel,  IndentureActivator indentureActivator) {
        this.wikiId = wikiId;
        this.incrLevel = incrLevel;
        this.initLevel = initLevel;
        this.indentureActivator = indentureActivator;
    }
    
    public String getWikiId() {
        return wikiId;
    }

    public int getIncrLevel() {
        return incrLevel;
    }

    public int getInitLevel() {
        return initLevel;
    }

    public IndentureActivator getIndentureActivator() {
        return indentureActivator;
    }
}