package cfvbaibai.cardfantasy.data;

import java.util.Arrays;
import java.util.HashSet;

public enum SkillType {

    /* 攻击力削弱技能 */
    削弱("30964", 10, SkillTag.永久, SkillTag.基础攻击加成, SkillTag.抗不屈),
    群体削弱("30952", 5, SkillTag.永久, SkillTag.基础攻击加成, SkillTag.抗不屈),
    毒刃("",0,SkillTag.永久, SkillTag.原始体力加成, SkillTag.抗不屈,SkillTag.抗免疫),
    /*
     * 削弱可以被淬炼、种族之力抵消，但瘟疫不能
     */
    瘟疫("31149", 5, SkillTag.永久, SkillTag.基础攻击加成),
    凋零真言("",4,1,SkillTag.永久, SkillTag.基础攻击加成),   
    凋零陷阱_陷阱("", 3, 0, SkillTag.控制),
    凋零陷阱("", 10, 0, SkillType.凋零陷阱_陷阱, SkillTag.永久, SkillTag.基础攻击加成),
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
    亮银("", 220, 0, SkillTag.永久, SkillTag.基础攻击加成),
    嗜血("30940", 10, SkillTag.永久, SkillTag.基础攻击加成),
    透支("30946", 20, SkillTag.永久, SkillTag.基础攻击加成),
    过载("30946", 20, 30, SkillTag.永久, SkillTag.基础攻击加成),
    贪吃("",100, 20, SkillTag.永久, SkillTag.基础攻击加成),
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
    鬼王之怒("", 135, 0, SkillTag.额外攻击加成),
    连锁攻击("30934", 0, 25, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.物理攻击),
    趁胜追击("40090", 40, 10, SkillTag.独立攻击加成),
    背刺("30879", 40, SkillTag.独立攻击加成),
    复仇("40086", 40, 10, SkillTag.独立攻击加成),
    振奋("80192", 40, 10, SkillTag.独立攻击加成),
    会心一击("", 100, 0, SkillTag.独立攻击加成),
    奋战("", 40, 10, SkillTag.独立攻击加成),
    神兵召唤("", 0, SkillTag.额外攻击加成),
    觉醒神兵召唤("", 0, SkillTag.额外攻击加成, SkillTag.不可洗炼),
    厨具召唤("", 0, SkillTag.额外攻击加成),
    圣器召唤("", 0, SkillTag.额外攻击加成),

    虚弱("57000", 0, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.魔王无效),
    战争怒吼("57022", 0, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.魔王无效),

    /* HP 上限调整技能 */
    王国守护("30949", 50,SkillTag.抗毒刃),
    蛮荒守护("31147", 50,SkillTag.抗毒刃), 
    地狱守护("31143", 50,SkillTag.抗毒刃),
    森林守护("31122", 50,SkillTag.抗毒刃),
    本源守护("30936", 40,SkillTag.抗毒刃),
    神圣守护("31123", 50,SkillTag.抗毒刃),

    落雷("31132", 25, SkillTag.魔法),
    连环闪电("30959", 25, SkillTag.魔法),
    雷暴("31151", 25, SkillTag.魔法),
    雷神降临("", 70, 10, new int[] { 10, 15, 25, 35, 45, 50, 55, 65, 70, 75, 85, }, SkillTag.魔法),
    觉醒雷神降临("", 70, 10, new int[] { 10, 15, 25, 35, 45, 50, 55, 65, 70, 75, 85, }, SkillTag.魔法),
    冰弹("30961", 20, SkillTag.魔法),
    霜冻新星("30965", 20, SkillTag.魔法),
    暴风雪("31133", 20, SkillTag.魔法),
    寒霜冲击("", 40, 10, SkillTag.魔法),

    毒液("31150", 20),
    毒雾("31148", 20),
    毒云("31163", 20),

    燃烧("30971", 25),
    烈火焚神("31127", 20),
    天火("", 150, 50),

    火球("30969", 25, SkillTag.魔法),
    火墙("30950", 25, SkillTag.魔法),
    烈焰风暴("31128", 25, SkillTag.魔法),
    修罗地火攻("", 100, 10, 10, 10, SkillTag.魔法),
    天怒("", 0, 25, SkillType.天火, SkillTag.魔法),

    狙击("", 0, 0, new int[] { 0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250 }, SkillTag.抗免疫, SkillTag.狙击),
    武形秘箭("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    骤雨("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    二重狙击("", 0, 0, new int[] { 0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250 }, SkillTag.抗免疫, SkillTag.狙击),
    神箭三重奏("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    武形神箭("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    穿刺("30960", 15, SkillTag.抗免疫),
    精准打击("", 100, 10, SkillTag.抗免疫),
    格挡("30955", 20),
    钢铁之肤("", 200, 50),
    魔龙之血("", 650, 0, 110, 0, SkillTag.物理护甲),
    陷阱("30975", 1, SkillTag.控制),
    反击("30957", 20, SkillTag.抗免疫, SkillTag.反击),
    物理反弹("", 50, 0, SkillTag.抗免疫, SkillTag.反击),
    武形破剑击("", 100, 0, SkillTag.抗免疫, SkillTag.反击),
    

    守护("30973", 0, SkillTag.守护),
    神之守护("", 0, SkillTag.守护),
    祈祷("30954", 50),
    祈福("", 0, 2),    

    横扫("30941", 0, SkillTag.抗免疫, SkillTag.物理攻击),
    三千世界("30941", 0, SkillTag.抗免疫, SkillTag.物理攻击),
    闪避("30939", 20, 5, SkillTag.物理护甲),
    龙胆("30939", 100, 0, SkillTag.物理护甲),
    连击("", 0, SkillTag.物理攻击),

    治疗("30974", 25),
    甘霖("30930", 25),
    治疗之雾("57018", 80, 20),
    回春("30944", 30),
    月恩术("", 300, 50),
    圣母回声("", 99999, 99999),
    闭月("", 6, 0, SkillType.圣母回声),
    月神的护佑("", new int[] { 8, 10, 13, 15, 18, 20, 23, 25, 28, 30, 35 }),
    月神的触碰("", new int[] { 8, 10, 13, 15, 18, 20, 23, 25, 28, 30, 35 }),
    

    法力反射("30929", 30),
    反射装甲("", 0, SkillTag.即死, SkillTag.抗免疫, SkillTag.魔王无效),
    镜面装甲("", 210, 0, SkillTag.抗沉默),
    镜面("", 0, SkillTag.抗沉默),
    真理导言("", 6, 0, SkillTag.抗沉默),
    阿拉希血统("", 0, 0, SkillTag.抗沉默, SkillTag.额外攻击加成),
    魔甲("30931", 140, -10),
    盾刺("30972", 20, SkillTag.抗免疫, SkillTag.反击),
    荆棘术("30972", 160, 40, SkillTag.抗免疫, SkillTag.反击),

    转生("30935", 30, 5),
    武形秘仪("", 70, 0),
    武形秘术("", 65, 0, 210, 0),
    我还会回来的("", 0),
    蛮荒我还会回来的("", 0),
    花族秘术("", 65, 0, 210, 0),

    裂伤("30963", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.魔王无效),
    全体裂伤("", 0, SkillTag.抗免疫, SkillTag.抗不屈),
    脱困("30956", 0),
    群体脱困("", 0),
    群体追击("", 15, SkillTag.额外攻击加成),
    复活("30953", 0, SkillTag.复活),
    送还("30943", 0, SkillTag.即死),
    冰甲("30937", 190, -10, SkillTag.物理护甲),
    神魔之甲("", 60, 0, 110, 0, SkillTag.物理护甲),
    冰神附体("", 140, 0, SkillTag.物理护甲),
    水流护甲("", 650, -50, 0, 50),
    骑士守护("", 0, SkillTag.不可洗炼),
    自爆("30945", 40),
    免疫("30942", 0),
    动能追加("", 0),
    不动("30928", 0, SkillTag.不动),
    灵魂封禁("", 0, 0, SkillTag.不动),
    神威("", 0, SkillTag.不动),
    月之守护("", 0, 0, new int[] { 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23 }, SkillTag.不动),
    洪荒之术("", 55, 0, SkillTag.不动),
    王之守护("", 0, SkillTag.不动, SkillTag.守护),
    传送("31331", 0, SkillTag.即死),
    弱点攻击("30881", 0),
    灵巧("57020", 0),
    灵魂禁锢("", 0),
    沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效),
    觉醒沉默("", 0, SkillTag.不可洗炼, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效),
    觉醒沉默A("", 0, SkillTag.不可洗炼, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效),
    全体沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效),
    无限全体沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默),
    灵魂消散("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默),

    回魂("31131", 1),
    上层精灵的挽歌("", 2, 0),
    迷魂("31129", 30, 5, SkillTag.控制, SkillTag.魔王无效),
    混乱领域("", 30, 5, SkillTag.控制, SkillTag.魔王无效),
    国色("", 60, 0, SkillTag.控制, SkillTag.魔王无效),
    精神狂乱("", 0),
    离间("", 0),
    无我境界("", 70, 0, SkillTag.控制, SkillTag.魔王无效),

    吸血("31135", 10),
    恶灵汲取("", 0, 3, SkillTag.抗免疫, SkillTag.魔王无效),
    反噬("31156", 50),
    疾病("31155", 10),

    诅咒("31145", 30),
    摧毁("31332", 0, SkillTag.即死, SkillTag.抗不屈),
    武形天火击("", 0, SkillTag.即死, SkillTag.抗不屈),
    咆哮("", 0, SkillTag.即死, SkillTag.抗不屈),

    封印("31157", 0, SkillTag.控制),
    血炼("31136", 20, SkillTag.魔法),
    鲜血盛宴("54209", 0, 20, SkillTag.魔法),
    歃血魔咒("", 50, 50, SkillTag.魔法),
    猎杀之夜("", 450, 0, SkillTag.魔法, SkillTag.抗沉默),
    天谴("31137", 20),
    末世术("", 180, 20),
    聚能护甲("31738", 50, SkillTag.不可洗炼),
    群体护甲("31607", 30, SkillTag.不可洗炼),
    
    全体阻碍("31158", 0, 1),
    阻碍("31159", 0, 1),
    加速("57019", 0, 1),
    全体加速("56751", 0, 1),

    净化("31160", 0),
    净魂领域("", 0),
    神性祈求("", 0),

    王国之盾("40099", 15, 5, SkillTag.种族之盾),
    森林之盾("40091", 15, 5, SkillTag.种族之盾),
    蛮荒之盾("40097", 15, 5, SkillTag.种族之盾),
    地狱之盾("40098", 15, 5, SkillTag.种族之盾),

    圣炎("43446", 0),
    扼杀("", 0),
    法力侵蚀("43447", 0, 20, 3, 0),
    破魔手("", 0, 20, 3, 0),
    灵王的轰击("", 250, 50, 3, 0),
    觉醒灵王的轰击("", 250, 50, 3, 0, SkillTag.不可洗炼),
    法力风暴("", 0, 20, 3, 0),
    魔法毁灭("", 100, 0, 10, 0),
    九转秘术("", 0, 0),

    大地之盾("80193", 0, SkillTag.控制, SkillTag.抗免疫, SkillTag.魔王无效),
    一闪("", 50, 0, SkillTag.控制, SkillTag.抗免疫, SkillTag.魔王无效),
    圣盾("56750", 0),

    英雄杀手("36260", 0, 15, SkillTag.额外攻击加成, SkillTag.不可洗炼),

    不屈("56962", 0, 0),
    时光倒流("80196", 0, 0, SkillTag.抗免疫, SkillTag.魔王无效),
    时间溯行("80196", 0, 0, SkillTag.即死, SkillTag.魔王无效),
    死亡印记("56754", 0, 50, SkillTag.魔王无效),
    武形印记("", 0, 200),
    闪光弹("", 5, 1),
    致盲("", 1, 1),
    魔力法阵("", 15, 15, SkillTag.魔王无效),
    魔力印记("", 15, 15),

    燕返("", 0, 0),
    斩杀("", 50, 0),
    送葬之刃("", 50, 5),
    无双("", 70, 0),

    星云锁链("", 0, 0),
    生命链接("", 0),
    逃跑("", 0, SkillTag.复活),
    夺魂("", 0, 1, SkillTag.复活),

    镜像("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    虚梦("", 0, 0, SkillType.传送, SkillTag.召唤, SkillTag.不可洗炼),
    召唤王国战士("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤骷髅战士("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
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
    召唤北海神兽("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤梦境女神("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    酋长号令("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤花族守卫("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤花族侍卫("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    七十二变("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    英灵降临("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    星之所在("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    灵龙轰咆("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),

    圣光洗礼("", 0, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效),
    森林沐浴("", 1, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效),
    蛮荒威压("", 2, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效),
    地狱同化("", 3, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效),
    
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

    魔神之刃("", 0, 0, new int[] { 1000, 1000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000 }, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.狙击),
    魔神之甲("", 0, 500, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.反击),
    魔神之咒("", 0, 1000, SkillTag.抗免疫, SkillTag.不可洗炼),
    // 用于处理各技能中"对魔神无效"的描述
    无效("", 0, 0, SkillTag.不可洗炼),
    物品技能("",0),

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
    private int[] impact3;
    private HashSet <SkillTag> tags;
    private SkillType attachedType;

    SkillType(String wikiId, int[] impact3, SkillTag ... tags) {
        this(wikiId, 0, 0, impact3, tags);
    }

    SkillType(String wikiId, int incrImpact, SkillTag ... tags) {
        this(wikiId, 0, incrImpact, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, 0, 0, tags);
    }
    
    SkillType(String wikiId, int initImpact, int incrImpact, SkillType attachedType, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, 0, 0, null, attachedType, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int[] impact3, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, 0, 0, impact3, null, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int initImpact2, int incrImpact2, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, initImpact2, incrImpact2, null, null, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int initImpact2, int incrImpact2, int[] impact3, SkillType attachedType, SkillTag ... tags) {
        this.wikiId = wikiId;
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
        this.initImpact2 = initImpact2;
        this.incrImpact2 = incrImpact2;
        if (impact3 != null) {
            this.impact3 = Arrays.copyOf(impact3, impact3.length);
        }
        this.attachedType = attachedType;
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
        return this.initImpact + level * this.incrImpact;
    }

    public int getImpact2(int level) {
        return this.initImpact2 + level * this.incrImpact2;
    }

    public int getImpact3(int level) {
        if (this.impact3 == null || level < 0 || level >= this.impact3.length) {
            return 0;
        }
        return this.impact3[level];
    }

    public boolean containsTag(SkillTag tag) {
        return this.tags.contains(tag);
    }

    public boolean isGrowable() {
        return this.impact3 != null || this.incrImpact != 0 || this.initImpact != 0;
    }

    public SkillType getAttachedType() {
        return this.attachedType;
    }
}