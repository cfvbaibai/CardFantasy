package cfvbaibai.cardfantasy.data;

import java.util.HashSet;

public enum FeatureType {

    /* 攻击力削弱技能 */
    削弱("30964", 10, FeatureTag.永久, FeatureTag.基础攻击加成),
    群体削弱("30952", 5, FeatureTag.永久, FeatureTag.基础攻击加成),

    /*
     * 削弱可以被淬炼、种族之力抵消，但瘟疫不能
     */
    瘟疫("31149", 5, FeatureTag.永久, FeatureTag.基础攻击加成),
    
    /* 攻击力加成技能 */
    /**
     * 基础攻击力=初始攻击力*军团战力加成+淬杀+种族之力+本源之力+怒涛加成+嗜血加成+洞察加成+邪灵汲取。魔神战时洞察无效。
     * 提升伤害=基础攻击力*(暴击加成+战意加成+种族相克加成+穷追猛打加成)，魔神战时的种族相克、乘胜追击无效。
     * 额外伤害：背刺、复仇、乘胜追击。额外伤害不受技能提升加成。
     * 总体伤害值=基础攻击力+提升伤害+额外伤害后，小数点向下取整。
     */
    献祭("31144", 20, 10, FeatureTag.永久, FeatureTag.基础攻击加成),
    群攻提升("31754", 15, FeatureTag.基础攻击加成),
    狂热("30880", 10, FeatureTag.永久, FeatureTag.基础攻击加成),
    嗜血("30940", 10, FeatureTag.永久, FeatureTag.基础攻击加成),
    透支("30946", 20, FeatureTag.永久, FeatureTag.基础攻击加成),
    邪灵汲取("43445", 0, 3, FeatureTag.抗免疫, FeatureTag.永久, FeatureTag.基础攻击加成),
    森林之力("31121", 25, FeatureTag.基础攻击加成),
    地狱之力("31140", 25, FeatureTag.基础攻击加成),
    蛮荒之力("31141", 25, FeatureTag.基础攻击加成),
    王国之力("30932", 25, FeatureTag.基础攻击加成),
    本源之力("30938", 20, FeatureTag.基础攻击加成),
    圣光("30947", 15, 15, FeatureTag.额外攻击加成),
    要害("31124", 15, 15, FeatureTag.额外攻击加成),
    暗杀("31138", 15, 15, FeatureTag.额外攻击加成),
    污染("31139", 15, 15, FeatureTag.额外攻击加成),
    暴击("30948", 20, FeatureTag.额外攻击加成),
    穷追猛打("30958", 15, FeatureTag.额外攻击加成),
    战意("31125", 15, FeatureTag.额外攻击加成),
    连锁攻击("30934", 0, 25, FeatureTag.额外攻击加成, FeatureTag.抗免疫, FeatureTag.物理攻击),
    趁胜追击("40090", 40, 10, FeatureTag.独立攻击加成),
    背刺("30879", 40, FeatureTag.独立攻击加成),
    复仇("40086", 40, 10, FeatureTag.独立攻击加成),

    虚弱("57000", 0, FeatureTag.额外攻击加成),

    /* HP 上限调整技能 */
    王国守护("30949", 50),
    蛮荒守护("31147", 50),
    地狱守护("31143", 50),
    森林守护("31122", 50),
    本源守护("30936", 40),
    神圣守护("31123", 50),

    狙击("30962", 25, FeatureTag.抗免疫),
    连环闪电("30959", 25, FeatureTag.魔法),
    穿刺("30960", 15, FeatureTag.抗免疫),
    格挡("30955", 20),
    陷阱("30975", 1, FeatureTag.控制),
    反击("30957", 20, FeatureTag.抗免疫),
    火球("30969", 25, FeatureTag.魔法),
    冰弹("30961", 20, FeatureTag.魔法),
    守护("30973", 0),

    横扫("30941", 0, FeatureTag.抗免疫, FeatureTag.物理攻击),
    闪避("30939", 20, 5, FeatureTag.物理护甲),
    治疗("30974", 25),
    甘霖("30930", 25),
    治疗之雾("57018", 80, 20),
    回春("30944", 30),

    祈祷("30954", 50),
    法力反射("30929", 30),
    魔甲("30931", 140, -10),
    盾刺("30972", 20, FeatureTag.抗免疫),
    燃烧("30971", 25),
    火墙("30950", 25, FeatureTag.魔法),
    烈焰风暴("31128", 25, FeatureTag.魔法),
    雷暴("31151", 25, FeatureTag.魔法),
    转生("30935", 30, 5),

    霜冻新星("30965", 20, FeatureTag.魔法),
    暴风雪("31133", 20, FeatureTag.魔法),
    裂伤("30963", 0, FeatureTag.抗免疫),
    落雷("31132", 25, FeatureTag.魔法),
    脱困("30956", 0),
    群体脱困("", 0),
    不动("30928", 0),
    复活("30953", 0, FeatureTag.复活),
    送还("30943", 0, FeatureTag.即死),
    冰甲("30937", 190, -10, FeatureTag.物理护甲),
    自爆("30945", 40),
    免疫("30942", 0),
    
    传送("31331", 0, FeatureTag.即死),
    弱点攻击("30881", 0),
    
    回魂("31131", 1),
    二重狙击("31130", 25, FeatureTag.抗免疫),
    迷魂("31129", 30, 5, FeatureTag.控制),
    烈火焚神("31127", 20),

    毒液("31150", 20),
    毒雾("31148", 20),
    毒云("31163", 20),
    吸血("31135", 10),
    反噬("31156", 50),
    疾病("31155", 10),

    诅咒("31145", 30),
    摧毁("31332", 0, FeatureTag.即死),

    封印("31157", 0, FeatureTag.控制),
    血炼("31136", 20, FeatureTag.魔法),
    鲜血盛宴("54209", 0, 20, FeatureTag.魔法),
    天谴("31137", 20),
    聚能护甲("31738", 50, FeatureTag.不可洗炼),
    群体护甲("31607", 30, FeatureTag.不可洗炼),
    
    全体阻碍("31158", 1, 1),
    阻碍("31159", 0, 1),
    加速("57019", 0, 1),
    全体加速("56751", 0, 1),
    净化("31160", 0),
    神性祈求("", 0),

    王国之盾("40099", 15, 5),
    森林之盾("40091", 15, 5),
    蛮荒之盾("40097", 15, 5),
    地狱之盾("40098", 15, 5),
    
    圣炎("43446", 0),
    法力侵蚀("43447", 20),

    英雄杀手("36260", 0, 15, FeatureTag.额外攻击加成, FeatureTag.不可洗炼),

    军团王国之力("", 0, 3, FeatureTag.永久, FeatureTag.原始攻击加成, FeatureTag.不可洗炼),
    军团森林之力("", 0, 3, FeatureTag.永久, FeatureTag.原始攻击加成, FeatureTag.不可洗炼),
    军团蛮荒之力("", 0, 3, FeatureTag.永久, FeatureTag.原始攻击加成, FeatureTag.不可洗炼),
    军团地狱之力("", 0, 3, FeatureTag.永久, FeatureTag.原始攻击加成, FeatureTag.不可洗炼),
    军团魔神之力("", 0, FeatureTag.不可洗炼),
    军团萌货之力("", 0, FeatureTag.不可洗炼),
    
    魔神之刃("", 2000, 0, FeatureTag.抗免疫, FeatureTag.不可洗炼),
    魔神之甲("", 1500, 0, FeatureTag.抗免疫, FeatureTag.不可洗炼),
    魔神之咒("", 1000, 0, FeatureTag.抗免疫, FeatureTag.不可洗炼),

    关小黑屋("", 0, 1, FeatureTag.即死),
    吐槽("", 0, 1, FeatureTag.控制),
    被插出五星("", 0, 1),

    自动扣血("", 0, 0, FeatureTag.抗守护, FeatureTag.不可洗炼),
    未知("", 0, FeatureTag.不可洗炼);

    private String wikiId;
    private int initImpact;
    private int incrImpact;
    private HashSet <FeatureTag> tags;
    
    FeatureType(String wikiId, int incrImpact, FeatureTag ... tags) {
        this(wikiId, 0, incrImpact, tags);
    }
    
    FeatureType(String wikiId, int initImpact, int incrImpact, FeatureTag ... tags) {
        this.wikiId = wikiId;
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
        this.tags = new HashSet <FeatureTag> ();
        for (FeatureTag tag : tags) {
            this.tags.add(tag);
        }
    }
    
    public String getWikiId() {
        return this.wikiId;
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
        return this.incrImpact != 0 || this.initImpact != 0;
    }
}