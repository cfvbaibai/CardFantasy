package cfvbaibai.cardfantasy.data;

import java.util.HashSet;

public enum FeatureType {
    狙击(25, FeatureTag.抗免疫),
    连环闪电(25, FeatureTag.魔法),
    穿刺(15, FeatureTag.抗免疫),
    圣光(15, 15),
    格挡(20),
    陷阱(1, FeatureTag.陷阱),
    反击(20, FeatureTag.抗免疫),
    削弱(10),
    火球(25, FeatureTag.魔法),
    冰弹(20, FeatureTag.魔法),
    暴击(20),
    守护(0),
    回春(30),
    狂热(10, FeatureTag.永久),
    横扫(0),
    闪避(20, 5, FeatureTag.物理护甲),
    治疗(25),
    甘霖(25),
    祈祷(50),
    王国之力(25),
    法力反射(30),
    魔甲(140, -10),
    盾刺(20, FeatureTag.抗免疫),
    燃烧(25),
    火墙(25, FeatureTag.魔法),
    烈焰风暴(25, FeatureTag.魔法),
    雷暴(25, FeatureTag.魔法),
    转生(30, 5),
    王国守护(50),
    霜冻新星(20, FeatureTag.魔法),
    暴风雪(20, FeatureTag.魔法),
    裂伤(0, FeatureTag.抗免疫),
    落雷(25, FeatureTag.魔法),
    穷追猛打(15),
    脱困(0),
    不动(0),
    复活(0, FeatureTag.复活),
    背刺(40),
    送还(0, FeatureTag.即死),
    群体削弱(5),
    冰甲(190, -10, FeatureTag.物理护甲),
    自爆(40),
    透支(20),
    免疫(0),
    嗜血(10, FeatureTag.永久),
    本源守护(40),
    本源之力(20),
    连锁攻击(25),
    传送(0, FeatureTag.即死),
    弱点攻击(0),
    要害(15, 15),
    森林之力(25),
    森林守护(50),
    回魂(1),
    二重狙击(25, FeatureTag.抗免疫),
    迷魂(30, 5),
    烈火焚神(20),
    战意(15),
    暗杀(15, 15),
    毒液(20),
    毒雾(20),
    毒云(20),
    吸血(10),
    反噬(50),
    疾病(10),
    蛮荒之力(25),
    蛮荒守护(50),
    诅咒(30),
    摧毁(0, FeatureTag.即死),
    献祭(20, 10),
    瘟疫(5),
    污染(15, 15),
    地狱之力(25),
    地狱守护(50),
    封印(0, FeatureTag.陷阱),
    血炼(20),
    天谴(20),
    聚能护甲(50),
    群体护甲(30),
    群攻提升(15),
    王国之盾(15, 5),
    森林之盾(15, 5),
    蛮荒之盾(15, 5),
    地狱之盾(15, 5),
    趁胜追击(40, 10),
    神圣守护(50),
    复仇(40, 10),
    圣炎(0),
    法力侵蚀(20),
    邪灵汲取(0, 3, FeatureTag.抗免疫),
    
    军团王国之力(0, 3),
    军团森林之力(0, 3),
    军团蛮荒之力(0, 3),
    军团地狱之力(0, 3),
    军团魔神之力(0),
    
    魔神之刃(2000, 0, FeatureTag.抗免疫),
    魔神之甲(1500, 0, FeatureTag.抗免疫),
    魔神之咒(1000, 0, FeatureTag.抗免疫),
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
