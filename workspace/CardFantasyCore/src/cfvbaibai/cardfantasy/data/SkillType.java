package cfvbaibai.cardfantasy.data;

import java.util.HashSet;

public enum SkillType {

    /* 攻击力削弱技能 */
    削弱("30964", 10, SkillTag.永久, SkillTag.基础攻击加成, SkillTag.抗不屈),
    群体削弱("30952", 5, SkillTag.永久, SkillTag.基础攻击加成, SkillTag.抗不屈),

    /*
     * 削弱可以被淬炼、种族之力抵消，但瘟疫不能
     */
    瘟疫("31149", 5, SkillTag.永久, SkillTag.基础攻击加成),
    
    /* 攻击力加成技能 */
    /**
     * 基础攻击力=初始攻击力*军团战力加成+淬杀+种族之力+本源之力+怒涛加成+嗜血加成+洞察加成+邪灵汲取。魔神战时洞察无效。
     * 提升伤害=基础攻击力*(暴击加成+战意加成+种族相克加成+穷追猛打加成)，魔神战时的种族相克、乘胜追击无效。
     * 额外伤害：背刺、复仇、乘胜追击。额外伤害不受技能提升加成。
     * 总体伤害值=基础攻击力+提升伤害+额外伤害后，小数点向下取整。
     */
    献祭("31144", 20, 10, SkillTag.永久, SkillTag.基础攻击加成),
    群攻提升("31754", 15, SkillTag.基础攻击加成, SkillTag.抗削弱),
    狂热("30880", 10, SkillTag.永久, SkillTag.基础攻击加成),
    嗜血("30940", 10, SkillTag.永久, SkillTag.基础攻击加成),
    透支("30946", 20, SkillTag.永久, SkillTag.基础攻击加成),
    邪灵汲取("43445", 0, 3, SkillTag.抗免疫, SkillTag.永久, SkillTag.基础攻击加成, SkillTag.抗不屈),
    森林之力("31121", 25, SkillTag.基础攻击加成, SkillTag.抗削弱),
    地狱之力("31140", 25, SkillTag.基础攻击加成, SkillTag.抗削弱),
    蛮荒之力("31141", 25, SkillTag.基础攻击加成, SkillTag.抗削弱),
    王国之力("30932", 25, SkillTag.基础攻击加成, SkillTag.抗削弱),
    本源之力("30938", 20, SkillTag.基础攻击加成, SkillTag.抗削弱),
    圣光("30947", 15, 15, SkillTag.额外攻击加成),
    要害("31124", 15, 15, SkillTag.额外攻击加成),
    暗杀("31138", 15, 15, SkillTag.额外攻击加成),
    污染("31139", 15, 15, SkillTag.额外攻击加成),
    暴击("30948", 20, SkillTag.额外攻击加成),
    凯撒之击("", 10, 5, SkillTag.额外攻击加成),
    穷追猛打("30958", 15, SkillTag.额外攻击加成),
    战意("31125", 15, SkillTag.额外攻击加成),
    连锁攻击("30934", 0, 25, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.物理攻击),
    趁胜追击("40090", 40, 10, SkillTag.独立攻击加成),
    背刺("30879", 40, SkillTag.独立攻击加成),
    复仇("40086", 40, 10, SkillTag.独立攻击加成),
    振奋("80192", 40, 10, SkillTag.独立攻击加成),
    奋战("", 40, 10, SkillTag.独立攻击加成),

    虚弱("57000", 0, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.抗不屈),
    战争怒吼("57022", 0, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.抗不屈),

    /* HP 上限调整技能 */
    王国守护("30949", 50),
    蛮荒守护("31147", 50), 
    地狱守护("31143", 50),
    森林守护("31122", 50),
    本源守护("30936", 40),
    神圣守护("31123", 50),

    狙击("30962", 25, SkillTag.抗免疫),
    连环闪电("30959", 25, SkillTag.魔法),
    穿刺("30960", 15, SkillTag.抗免疫),
    格挡("30955", 20),
    陷阱("30975", 1, SkillTag.控制),
    反击("30957", 20, SkillTag.抗免疫, SkillTag.反击),
    火球("30969", 25, SkillTag.魔法),
    冰弹("30961", 20, SkillTag.魔法),
    守护("30973", 0),

    横扫("30941", 0, SkillTag.抗免疫, SkillTag.物理攻击),
    闪避("30939", 20, 5, SkillTag.物理护甲),
    治疗("30974", 25),
    甘霖("30930", 25),
    治疗之雾("57018", 80, 20),
    回春("30944", 30),
    月神的护佑("", new int[] { 8, 10, 13, 15, 18, 20, 23, 25, 28, 30, 35 }),
    月神的触碰("", new int[] { 8, 10, 13, 15, 18, 20, 23, 25, 28, 30, 35 }),

    祈祷("30954", 50),
    祈福("", 0, 2),
    法力反射("30929", 30),
    魔甲("30931", 140, -10),
    盾刺("30972", 20, SkillTag.抗免疫, SkillTag.反击),
    燃烧("30971", 25),
    火墙("30950", 25, SkillTag.魔法),
    烈焰风暴("31128", 25, SkillTag.魔法),
    雷暴("31151", 25, SkillTag.魔法),
    转生("30935", 30, 5),
    我还会回来的("", 0),

    霜冻新星("30965", 20, SkillTag.魔法),
    暴风雪("31133", 20, SkillTag.魔法),
    寒霜冲击("", 40, 10, SkillTag.魔法),
    裂伤("30963", 0, SkillTag.抗免疫, SkillTag.抗不屈),
    落雷("31132", 25, SkillTag.魔法),
    脱困("30956", 0),
    群体脱困("", 0),
    不动("30928", 0),
    复活("30953", 0, SkillTag.复活),
    送还("30943", 0, SkillTag.即死),
    冰甲("30937", 190, -10, SkillTag.物理护甲),
    自爆("30945", 40),
    免疫("30942", 0),
    动能追加("", 0),
    
    传送("31331", 0, SkillTag.即死),
    弱点攻击("30881", 0),
    灵巧("57020", 0),
    
    回魂("31131", 1),
    二重狙击("31130", 25, SkillTag.抗免疫),
    迷魂("31129", 30, 5, SkillTag.控制),
    精神狂乱("", 0),
    烈火焚神("31127", 20),

    毒液("31150", 20),
    毒雾("31148", 20),
    毒云("31163", 20),
    吸血("31135", 10),
    反噬("31156", 50),
    疾病("31155", 10),

    诅咒("31145", 30),
    摧毁("31332", 0, SkillTag.即死, SkillTag.抗不屈),

    封印("31157", 0, SkillTag.控制),
    血炼("31136", 20, SkillTag.魔法),
    鲜血盛宴("54209", 0, 20, SkillTag.魔法),
    天谴("31137", 20),
    聚能护甲("31738", 50, SkillTag.不可洗炼),
    群体护甲("31607", 30, SkillTag.不可洗炼),
    
    全体阻碍("31158", 0, 1),
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
    破魔手("", 0, 20),
    九转秘术("", 0, 0),

    大地之盾("80193", 0, SkillTag.控制, SkillTag.抗免疫),
    圣盾("56750", 0),

    英雄杀手("36260", 0, 15, SkillTag.额外攻击加成, SkillTag.不可洗炼),
    
    不屈("56962", 0, 0),
    时光倒流("80196", 0, 0, SkillTag.抗免疫),
    死亡印记("56754", 0, 50),
    闪光弹("", 5, 1),
    致盲("", 1, 1),
    魔力法阵("", 15, 15),
    魔力印记("", 15, 15),

    燕返("", 200, 0),

    修罗地火攻("", 100, 10, 10, 10, SkillTag.魔法),

    星云锁链("", 0, 0),

    召唤王国战士("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤邪龙护卫("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤噩梦护卫("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤复仇护卫("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤花仙子("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤火焰乌鸦("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤人马巡逻者("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤女神侍者("80191", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤树人守护者("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤炎魔("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    双子之身("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),

    圣光洗礼("", 0, 0, SkillTag.抗免疫, SkillTag.不可洗炼),
    森林沐浴("", 1, 0, SkillTag.抗免疫, SkillTag.不可洗炼),
    蛮荒威压("", 2, 0, SkillTag.抗免疫, SkillTag.不可洗炼),
    地狱同化("", 3, 0, SkillTag.抗免疫, SkillTag.不可洗炼),
    
    王国同调("", 0, 1, SkillTag.基础攻击加成),
    森林同调("", 0, 1, SkillTag.基础攻击加成),
    蛮荒同调("", 0, 1, SkillTag.基础攻击加成),
    地狱同调("", 0, 1, SkillTag.基础攻击加成),
    
    军团王国之力("", 0, 3, SkillTag.永久, SkillTag.原始攻击加成, SkillTag.原始体力加成, SkillTag.不可洗炼),
    军团森林之力("", 0, 3, SkillTag.永久, SkillTag.原始攻击加成, SkillTag.原始体力加成, SkillTag.不可洗炼),
    军团蛮荒之力("", 0, 3, SkillTag.永久, SkillTag.原始攻击加成, SkillTag.原始体力加成, SkillTag.不可洗炼),
    军团地狱之力("", 0, 3, SkillTag.永久, SkillTag.原始攻击加成, SkillTag.原始体力加成, SkillTag.不可洗炼),
    军团魔神之力("", 0, SkillTag.不可洗炼),
    军团萌货之力("", 0, SkillTag.不可洗炼),
    原始攻击调整("", 0, 1, SkillTag.永久, SkillTag.原始攻击加成, SkillTag.不可洗炼),
    原始体力调整("", 0, 1, SkillTag.永久, SkillTag.原始体力加成, SkillTag.不可洗炼),

    魔神之刃("", 2000, 0, SkillTag.抗免疫, SkillTag.不可洗炼),
    魔神之甲("", 1500, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.反击),
    魔神之咒("", 1000, 0, SkillTag.抗免疫, SkillTag.不可洗炼),
    // 用于处理各技能中"对魔神无效"的描述
    无效("", 0, 0, SkillTag.不可洗炼),

    关小黑屋("", 0, 1, SkillTag.即死),
    吐槽("", 0, 1, SkillTag.控制),
    被插出五星("", 0, 1),

    自动扣血("", 0, 0, SkillTag.抗守护, SkillTag.不可洗炼),
    未知("", 0, SkillTag.不可洗炼);

    private String wikiId;
    private int initImpact;
    private int incrImpact;
    private int initImpact2;
    private int incrImpact2;
    private int[] impactList;
    private HashSet <SkillTag> tags;
    
    SkillType(String wikiId, int incrImpact, SkillTag ... tags) {
        this(wikiId, 0, incrImpact, tags);
    }
    
    SkillType(String wikiId, int initImpact, int incrImpact, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, 0, 0, tags);
    }
    
    SkillType(String wikiId, int initImpact, int incrImpact, int initImpact2, int incrImpact2, SkillTag ... tags) {
        this.wikiId = wikiId;
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
        this.initImpact2 = initImpact2;
        this.incrImpact2 = incrImpact2;
        this.tags = new HashSet <SkillTag> ();
        for (SkillTag tag : tags) {
            this.tags.add(tag);
        }
    }
    
    SkillType(String wikiId, int[] impactList, SkillTag ... tags) {
        this.impactList = impactList;
        this.tags = new HashSet <SkillTag> ();
        for (SkillTag tag : tags) {
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
        if (this.impactList != null && this.impactList.length > level) {
            return this.impactList[level];
        }
        return this.initImpact + level * this.incrImpact;
    }
    
    public int getImpact2(int level) {
        return this.initImpact2 + level * this.incrImpact2;
    }
    
    public boolean containsTag(SkillTag tag) {
        return this.tags.contains(tag);
    }

    public boolean isGrowable() {
        return this.impactList == null && (this.incrImpact != 0 || this.initImpact != 0);
    }
}