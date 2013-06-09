package cfvbaibai.cardfantasy.data;

import java.util.HashSet;

public enum FeatureType {
    狙击(25),
    连环闪电(25, FeatureTag.魔法),
    穿刺(15),
    圣光(15, 15),
    格挡(20),
    陷阱(1),
    反击(20),
    削弱(10),
    火球(25, FeatureTag.魔法),
    冰弹(20, FeatureTag.魔法),
    暴击(20),
    守护(0),
    回春(30),
    狂热(10),
    横扫(0),
    闪避(20, 5),
    治疗(25),
    甘霖(25),
    祈祷(50),
    王国之力(25),
    法力反射(30),
    魔甲(140, -10),
    盾刺(20),
    燃烧(25),
    火墙(25, FeatureTag.魔法),
    烈焰风暴(25, FeatureTag.魔法),
    雷暴(25, FeatureTag.魔法),
    转生(30, 5),
    未知(0);
    

    private int initImpact;
    private int incrImpact;
    private HashSet <FeatureTag> tags;
    
    FeatureType(int incrImpact, FeatureTag ... tags) {
        this(0, incrImpact, tags);
    }
    
    FeatureType(int initImpact, int incrImpact, FeatureTag ... tags) {
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
        this.tags = new HashSet <FeatureTag> ();
        for (FeatureTag tag : tags) {
            this.tags.add(tag);
        }
    }
    
    public String getDisplayName() {
        return this.name();
    }

    public int getImpact(int level) {
        return initImpact + level * incrImpact;
    }
    
    public boolean containsTag(FeatureTag tag) {
        return this.tags.contains(tag);
    }
}
