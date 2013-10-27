package cfvbaibai.cardfantasy.data;

import java.util.HashSet;

public enum FeatureType {

    /* 攻击力削弱技能 */
    削弱(10, FeatureTag.永久, FeatureTag.基础攻击加成),
    群体削弱(5, FeatureTag.永久, FeatureTag.基础攻击加成),
    /*
     * 削弱可以被淬炼、种族之力抵消，但瘟疫不能
     */
    瘟疫(5, FeatureTag.永久, FeatureTag.基础攻击加成),
    
    /* 攻击力加成技能 */
    /**
     * 基础攻击力=初始攻击力*军团战力加成+淬杀+种族之力+本源之力+怒涛加成+嗜血加成+洞察加成+邪灵汲取。魔神战时洞察无效。
     * 提升伤害=基础攻击力*(暴击加成+战意加成+种族相克加成+乘胜追击加成)，魔神战时的种族相克、乘胜追击无效。
     * 额外伤害：背刺、复仇、穷追猛打。额外伤害不受技能提升加成。
     * 总体伤害值=基础攻击力+提升伤害+额外伤害后，小数点向上取整。
     */
    献祭(20, 10, FeatureTag.永久, FeatureTag.基础攻击加成),
    群攻提升(15, FeatureTag.基础攻击加成),
    狂热(10, FeatureTag.永久, FeatureTag.基础攻击加成),
    嗜血(10, FeatureTag.永久, FeatureTag.基础攻击加成),
    透支(20, FeatureTag.永久, FeatureTag.基础攻击加成),
    邪灵汲取(0, 3, FeatureTag.抗免疫, FeatureTag.永久, FeatureTag.基础攻击加成),
    森林之力(25, FeatureTag.基础攻击加成),
    地狱之力(25, FeatureTag.基础攻击加成),
    蛮荒之力(25, FeatureTag.基础攻击加成),
    王国之力(25, FeatureTag.基础攻击加成),
    本源之力(20, FeatureTag.基础攻击加成),
    圣光(15, 15, FeatureTag.额外攻击加成),
    要害(15, 15, FeatureTag.额外攻击加成),
    暗杀(15, 15, FeatureTag.额外攻击加成),
    污染(15, 15, FeatureTag.额外攻击加成),
    暴击(20, FeatureTag.额外攻击加成),
    穷追猛打(15, FeatureTag.额外攻击加成),
    战意(15, FeatureTag.额外攻击加成),
    连锁攻击(25, FeatureTag.额外攻击加成, FeatureTag.抗免疫, FeatureTag.物理攻击),
    趁胜追击(40, 10, FeatureTag.独立攻击加成),
    背刺(40, FeatureTag.独立攻击加成),
    复仇(40, 10, FeatureTag.独立攻击加成),

    /* HP 上限调整技能 */
    王国守护(50),
    蛮荒守护(50),
    地狱守护(50),
    森林守护(50),
    本源守护(40),
    神圣守护(50),

    狙击(25, FeatureTag.抗免疫),
    连环闪电(25, FeatureTag.魔法),
    穿刺(15, FeatureTag.抗免疫),
    格挡(20),
    陷阱(1, FeatureTag.陷阱),
    反击(20, FeatureTag.抗免疫),
    火球(25, FeatureTag.魔法),
    冰弹(20, FeatureTag.魔法),
    守护(0),
    回春(30),
    横扫(0, FeatureTag.抗免疫, FeatureTag.物理攻击),
    闪避(20, 5, FeatureTag.物理护甲),
    治疗(25),
    甘霖(25),
    祈祷(50),
    法力反射(30),
    魔甲(140, -10),
    盾刺(20, FeatureTag.抗免疫),
    燃烧(25),
    火墙(25, FeatureTag.魔法),
    烈焰风暴(25, FeatureTag.魔法),
    雷暴(25, FeatureTag.魔法),
    转生(30, 5),

    霜冻新星(20, FeatureTag.魔法),
    暴风雪(20, FeatureTag.魔法),
    裂伤(0, FeatureTag.抗免疫),
    落雷(25, FeatureTag.魔法),
    脱困(0),
    不动(0),
    复活(0, FeatureTag.复活),
    送还(0, FeatureTag.即死),
    冰甲(190, -10, FeatureTag.物理护甲),
    自爆(40),
    免疫(0),
    
    传送(0, FeatureTag.即死),
    弱点攻击(0),
    
    回魂(1),
    二重狙击(25, FeatureTag.抗免疫),
    迷魂(30, 5),
    烈火焚神(20),

    毒液(20),
    毒雾(20),
    毒云(20),
    吸血(10),
    反噬(50),
    疾病(10),

    诅咒(30),
    摧毁(0, FeatureTag.即死),

    封印(0, FeatureTag.陷阱),
    血炼(20),
    天谴(20),
    聚能护甲(50),
    群体护甲(30),

    王国之盾(15, 5),
    森林之盾(15, 5),
    蛮荒之盾(15, 5),
    地狱之盾(15, 5),
    
    圣炎(0),
    法力侵蚀(20),

    军团王国之力(0, 3, FeatureTag.永久, FeatureTag.基础攻击加成, FeatureTag.不可洗炼),
    军团森林之力(0, 3, FeatureTag.永久, FeatureTag.基础攻击加成, FeatureTag.不可洗炼),
    军团蛮荒之力(0, 3, FeatureTag.永久, FeatureTag.基础攻击加成, FeatureTag.不可洗炼),
    军团地狱之力(0, 3, FeatureTag.永久, FeatureTag.基础攻击加成, FeatureTag.不可洗炼),
    军团魔神之力(0, FeatureTag.不可洗炼),
    
    魔神之刃(2000, 0, FeatureTag.抗免疫, FeatureTag.不可洗炼),
    魔神之甲(1500, 0, FeatureTag.抗免疫, FeatureTag.不可洗炼),
    魔神之咒(1000, 0, FeatureTag.抗免疫, FeatureTag.不可洗炼),
    
    自动扣血(0, 0, FeatureTag.抗守护, FeatureTag.不可洗炼),
    未知(0, FeatureTag.不可洗炼);

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
        return this.initImpact + level * this.incrImpact;
    }
    
    public boolean containsTag(FeatureTag tag) {
        return this.tags.contains(tag);
    }

    public boolean isGrowable() {
        return this.incrImpact > 0;
    }
}