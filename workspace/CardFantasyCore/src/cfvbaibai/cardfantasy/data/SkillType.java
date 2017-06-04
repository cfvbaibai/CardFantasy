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
    /* 攻击力加成技能 */
    /**
     * 基础攻击力=初始攻击力*军团战力加成+淬杀+种族之力+本源之力+怒涛加成+嗜血加成+洞察加成+邪灵汲取。魔神战时洞察无效。
     * 提升伤害=基础攻击力*(暴击加成+战意加成+种族相克加成+穷追猛打加成)，魔神战时的种族相克、乘胜追击无效。
     * 额外伤害：背刺、复仇、乘胜追击。额外伤害不受技能提升加成。
     * 总体伤害值=基础攻击力+提升伤害+额外伤害后，小数点向下取整。
     */
    献祭("31144", 20, 10, SkillTag.永久, SkillTag.基础攻击加成),
    制衡("", 100, 0, SkillTag.永久, SkillTag.复活,SkillTag.基础攻击加成),
    灵魂献祭("", 100, 0, SkillTag.永久, SkillTag.复活,SkillTag.基础攻击加成),
    侵蚀("", 100, 0, SkillTag.永久, SkillTag.基础攻击加成),
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
    根源之力("", 0,20,0,40, SkillTag.基础攻击加成, SkillTag.抗削弱,SkillTag.抗毒刃),
    战歌之鼓("", 5, SkillTag.基础攻击加成,SkillTag.抗削弱),
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
    神兵降临("", 0, SkillTag.额外攻击加成, SkillTag.不动),
    觉醒神兵召唤("", 0, SkillTag.额外攻击加成, SkillTag.不可洗炼),
    厨具召唤("", 0, SkillTag.额外攻击加成),
    圣器召唤("", 0, SkillTag.额外攻击加成),

    虚弱("57000", 0, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.魔王无效, SkillTag.魔族天赋),
    战争怒吼("57022", 0, SkillTag.额外攻击加成, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.魔王无效, SkillTag.魔族天赋),

    /* HP 上限调整技能  */
    王国守护("30949", 50,SkillTag.抗毒刃),
    蛮荒守护("31147", 50,SkillTag.抗毒刃),
    地狱守护("31143", 50,SkillTag.抗毒刃),
    森林守护("31122", 50,SkillTag.抗毒刃),
    本源守护("30936", 40,SkillTag.抗毒刃),
    神圣守护("31123", 50,SkillTag.抗毒刃),
    生命符文("31123", 5,SkillTag.抗毒刃),

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
    凤火("", 500, 0),

    火球("30969", 25, SkillTag.魔法),
    火墙("30950", 25, SkillTag.魔法),
    烈焰风暴("31128", 25, SkillTag.魔法),
    修罗地火攻("", 100, 10, 10, 10, SkillTag.魔法),
    火攻("", 200, 0, 110, 10, SkillTag.魔法),
    凤鸣("",500,0,SkillTag.魔法),


    狙击("", 0, 0, new int[] { 0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250 }, SkillTag.抗免疫, SkillTag.狙击),
    穿云箭("", 0, 0, new int[] { 1200, 1200, 1200, 1200, 1200, 1200, 1200, 1200, 1200, 1200, 1200 }, SkillTag.抗免疫, SkillTag.狙击),
    武形秘箭("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    骤雨("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    二重狙击("", 0, 0, new int[] { 0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250 }, SkillTag.抗免疫, SkillTag.狙击),
    神箭三重奏("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    武形神箭("", 0, 0, new int[] { 220, 250, 270, 300, 320, 350, 370, 400, 420, 450, 500 }, SkillTag.抗免疫, SkillTag.狙击),
    穿刺("30960", 15, SkillTag.抗免疫),
    精准打击("", 100, 10, SkillTag.抗免疫),
    精准射击("", 250, 0, SkillTag.抗免疫),
    格挡("30955", 20),
    钢铁之肤("", 200, 50),
    魔龙之血("", 650, 0, 110, 0, SkillTag.物理护甲,SkillTag.沉默无效),
    陷阱("30975", 1, SkillTag.控制),
    反击("30957", 20, SkillTag.抗免疫, SkillTag.反击),
    物理反弹("", 50, 0, SkillTag.抗免疫, SkillTag.反击),
    武形破剑击("", 100, 0, SkillTag.抗免疫, SkillTag.反击),
    反击屏障("", 200, 0, SkillTag.抗免疫, SkillTag.反击),


    守护("30973", 0, SkillTag.守护),
    神之守护("", 0, SkillTag.守护),
    祈祷("30954", 50),
    祈福("", 0, 2),

    横扫("30941", 0, SkillTag.抗免疫, SkillTag.物理攻击),
    三千世界("30941", 0, SkillTag.抗免疫, SkillTag.物理攻击),
    毒杀("",0,SkillTag.永久, SkillTag.原始体力加成, SkillTag.抗不屈,SkillTag.抗免疫,SkillTag.物理攻击),
    鹰眼("",100,0,SkillTag.额外攻击加成),
    闪避("30939", 20, 5, SkillTag.物理护甲),
    龙胆("30939", 100, 0, SkillTag.物理护甲),
    鬼彻("30939", 70, 0, SkillTag.抗免疫, SkillTag.物理攻击),
    连击("", 0, SkillTag.物理攻击),

    治疗("30974", 25),
    甘霖("30930", 25),
    治疗之雾("57018", 80, 20),
    回春("30944", 30),
    月恩术("", 300, 50),
    圣母回声("", 99999, 99999),
    圣母吟咏("", 10),
    圣母咏叹调("", 99999, 99999,SkillTag.抗沉默),
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
    刚烈("", 800, 0, SkillTag.抗免疫, SkillTag.反击),

    转生("30935", 30, 5),
    武形秘仪("", 70, 0),
    武形秘术("", 65, 0, 210, 0),
    武形秘法("",70, 0),
    我还会回来的("", 0),
    蛮荒我还会回来的("", 0),
    花族秘术("", 65, 0, 210, 0),
    涅盘("", 100, 0),
    凤凰涅槃("", 100, 0),

    裂伤("30963", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.魔王无效),
    全体裂伤("", 0, SkillTag.抗免疫, SkillTag.抗不屈),
    脱困("30956", 0),
    群体脱困("", 0),
    群体追击("", 15, SkillTag.额外攻击加成),
    复活("30953", 0, SkillTag.复活),
    青囊("", 0, SkillTag.复活),
    送还("30943", 0, SkillTag.即死),
    冰甲("30937", 190, -10, SkillTag.物理护甲),
    寒冰之盾("", 100, 0,SkillTag.控制, SkillTag.抗免疫, SkillTag.魔王无效,SkillTag.物理护甲, SkillTag.魔族天赋),
    神魔之甲("", 60, 0, 110, 0, SkillTag.物理护甲),
    冰神附体("", 140, 0, SkillTag.物理护甲),
    水流护甲("", 650, -50, 0, 50),
    骑士守护("", 0, SkillTag.不可洗炼),
    骑士荣耀("",0,SkillTag.不可洗炼,SkillTag.不动),
    自爆("30945", 40),
    免疫("30942", 0),
    结界立场("", 0,SkillTag.抗沉默),
    动能追加("", 0),
    不动("30928", 0, SkillTag.不动),
    坚毅("", 0, SkillTag.不动),
    灵魂封禁("", 0, 0, SkillTag.不动),
    神威("", 0, SkillTag.不动),
    月之守护("", 0, 0, new int[] { 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23 }, SkillTag.不动),
    洪荒之术("", 55, 0, SkillTag.不动),
    六道轮回("", 65, 0, SkillTag.不动),
    王之守护("", 0, SkillTag.不动, SkillTag.守护),
    武侯("", 50,0,  SkillTag.守护),
    传送("31331", 0, SkillTag.即死),
    代表月亮消灭你("", 0,SkillTag.即死),
    弱点攻击("30881", 0),
    破军("",45,5),
    灵巧("57020", 0),
    灵魂禁锢("", 0),
    沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效, SkillTag.魔族天赋),
    觉醒沉默("", 0, SkillTag.不可洗炼, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效, SkillTag.魔族天赋),
    觉醒沉默A("", 0, SkillTag.不可洗炼, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效, SkillTag.魔族天赋),
    全体沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效, SkillTag.魔族天赋),
    全领域沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效, SkillTag.魔族天赋),
    无限全体沉默("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默, SkillTag.魔王无效, SkillTag.魔族天赋),
    灵魂消散("", 0, SkillTag.抗免疫, SkillTag.抗不屈, SkillTag.沉默),
    连斩("",0),
    魔族之血("",650,0),


    回魂("31131", 1),
    祈愿("", 1),
    上层精灵的挽歌("", 2, 0),
    迷魂("31129", 30, 5, SkillTag.控制, SkillTag.魔王无效),
    混乱领域("", 30, 5, SkillTag.控制, SkillTag.魔王无效),
    国色("", 60, 0, SkillTag.控制, SkillTag.魔王无效),
    魅惑之舞("", 60, 0, SkillTag.控制, SkillTag.魔王无效),
    精神狂乱("", 0),
    离间("", 0),
    无尽华尔兹("", 0),
    精神污染("", 10),
    无我境界("", 70, 0, SkillTag.控制, SkillTag.魔王无效),

    吸血("31135", 10),
    蛇吻("", 70, 0, SkillTag.不动),
    武圣("",70, 0),
    村正("",70, 0),
    恶灵汲取("", 0, 3, SkillTag.抗免疫, SkillTag.魔王无效),
    反噬("31156", 50),
    疾病("31155", 10),

    诅咒("31145", 30),
    摧毁("31332", 0, SkillTag.即死, SkillTag.抗不屈),
    武形天火击("", 0, SkillTag.即死, SkillTag.抗不屈),
    咆哮("", 0, SkillTag.即死, SkillTag.抗不屈),

    炼金失败("",0,SkillTag.复活),

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
    九转秘术("", 0, 0),

    法力侵蚀("43447", 0, 20, 3, 0, SkillTag.抗免疫),
    破魔手("", 0, 20, 3, 0, SkillTag.抗免疫),
    灵王的轰击("", 250, 50, 3, 0, SkillTag.抗免疫),
    觉醒灵王的轰击("", 250, 50, 3, 0, SkillTag.不可洗炼, SkillTag.抗免疫),
    法力风暴("", 0, 20, 3, 0, SkillTag.抗免疫),
    魔法毁灭("", 100, 0, 10, 0, SkillTag.抗免疫),
    赤之魔枪("", 200, 50, 3, 0, SkillTag.抗免疫),
    寒冰触碰("", 250, 50, 3, 0, new int[] {50,50,50,50,50,50,50,50,50,50}, SkillTag.抗免疫),
    雷霆一击("", 200, 75, 3, 0, new int[] {75,75,75,75,75,75,75,75,75,75}, SkillTag.抗免疫),
    雷霆之怒("", 200, 75, 3, 0, new int[] {75,75,75,75,75,75,75,75,75,75}, SkillTag.抗免疫),

    大地之盾("80193", 0, SkillTag.控制, SkillTag.抗免疫, SkillTag.魔王无效, SkillTag.魔族天赋),
    一闪("", 50, 0, SkillTag.控制, SkillTag.抗免疫, SkillTag.魔王无效, SkillTag.魔族天赋),
    圣盾("56750", 0),
    无刀取("", 0),


    英雄杀手("36260", 0, 15, SkillTag.额外攻击加成, SkillTag.不可洗炼),

    不屈("56962", 0, 0),
    空城("",0,0),
    时光倒流("80196", 0, 0, SkillTag.抗免疫, SkillTag.魔王无效, SkillTag.魔族天赋),
    时间溯行("80196", 0, 0, SkillTag.即死, SkillTag.魔王无效, SkillTag.魔族天赋),
    死亡印记("56754", 0, 50, SkillTag.魔王无效),
    武形印记("", 0, 200),
    闪光弹("", 5, 1),
    致盲("", 1, 1),
    魔力法阵("", 15, 15, SkillTag.魔王无效),
    魔力印记("", 15, 15),
    东风("",175,0),

    燕返("", 0, 0),
    斩杀("", 50, 0),
    双斩("", 50, 0),
    送葬之刃("", 50, 5),
    无双("", 70, 0),

    星云锁链("", 0, 0),
    生命链接("", 0),
    逃跑("", 0),
    夺魂("", 0, 1, SkillTag.复活),

    镜像("", 0, 0, SkillTag.召唤),
    洛神("", 0, 0, SkillTag.召唤),
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
    寒霜召唤("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    星之所在("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    灵龙轰咆("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    无尽梦魇("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    百鬼夜行("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    武形降临("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    万兽奔腾("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    爱之召唤("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    仙子召唤("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤花舞剑士("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤玫瑰甜心("", 0, 0, SkillTag.召唤, SkillTag.不可洗炼),
    召唤玫瑰剑士("",SkillType.召唤花舞剑士,0, SkillType.召唤玫瑰甜心,0, SkillTag.不可洗炼),

    圣光洗礼("", 0, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效, SkillTag.魔族天赋),
    森林沐浴("", 1, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效, SkillTag.魔族天赋),
    蛮荒威压("", 2, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效, SkillTag.魔族天赋),
    地狱同化("", 3, 0, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.魔王无效, SkillTag.魔族天赋),

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
    铁壁("", 10),

    魔神之刃("", 0, 0, new int[] { 1000, 1000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000 }, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.狙击),
    魔神之甲("", 0, 500, SkillTag.抗免疫, SkillTag.不可洗炼, SkillTag.反击),
    魔神之咒("", 0, 1000, SkillTag.抗免疫, SkillTag.不可洗炼),
    // 用于处理各技能中"对魔神无效"的描述
    无效("", 0, 0, SkillTag.不可洗炼),
    // 用于处理各技能中"对魔族无效"的描述
    魔族天赋("", 0, 0),
    物品技能("",0),

    关小黑屋("", 0, 1, SkillTag.即死),
    吐槽("", 0, 1, SkillTag.控制),
    被插出五星("", 0, 1),

    自动扣血("", 0, 0, SkillTag.抗守护, SkillTag.不可洗炼),
    未知("", 0, SkillTag.不可洗炼),

    //合并技能中单个技能
    天崩地裂("",0,SkillTag.即死),
    地裂("",0,SkillTag.即死),

    // Merged Skills
    凋零陷阱("", SkillType.凋零真言, 6, SkillType.陷阱, 3),
    天怒("", SkillType.烈焰风暴, SkillType.天火),
    闭月("", SkillType.祈福, 3, SkillType.圣母回声, 0),
    虚梦("", SkillType.镜像, SkillType.传送),
    觉醒白虎守护("", SkillType.月神的护佑, 7, SkillType.祈福, 3),
    觉醒星之意志("", SkillType.灵魂消散, 0, SkillType.灵王的轰击, 7),
    觉醒狼顾("",  SkillType.雷神降临, 8, SkillType.雷霆一击, 5),
    鬼才("", SkillType.侵蚀,0,SkillType.夺魂,1),
    镜魔("",SkillType.镜面,0,SkillType.镜像,0 ,SkillTag.抗沉默),
    刀语("",SkillType.连击,0,SkillType.弱点攻击,0 ),
    逆鳞("",SkillType.武形秘箭,10,SkillType.神箭三重奏,10),
    突袭("",0,0, SkillTag.即死,SkillTag.额外攻击加成),
    樱魂("",SkillType.吸血,8,SkillType.奋战,7),
    LETITGO("", SkillType.送还,0,SkillType.反射装甲,0),
    百步穿杨("",SkillType.武形神箭,5,SkillType.穿云箭,1),
    业火("",SkillType.凤鸣,0,SkillType.凤火,0),
    魔龙吐息("",SkillType.凤鸣,0,SkillType.凤火,0),
    觉醒天崩地裂("", SkillType.天崩地裂,0,SkillType.灵王的轰击,4),

    新生("",0,0,SkillTag.新生),

    西凉铁骑("",SkillType.破军,5,null,0),
    袈裟斩("",SkillType.燕返,0,null,0),
    致命打击("",SkillType.精准打击,9,null,0),
    爱心料理("",SkillType.圣母吟咏,7,null,0),
    剑舞("",SkillType.神兵召唤,0,null,0),
    // To Do

    // 以下全部技能皆为待更新的空技能，请在更新之后将其移动到上面适合的位置
    // 对于物品技能，请将其（按顺序）加入下面的物品技能列表，用来以后更改自动卡牌数据更新工具

    // 物品技能列表：
    三星万能卡牌("", 0),
    四星万能卡牌("", 0),
    五星万能卡牌("", 0),

    // 待更新列表：
    化学风暴("", 0),
    变身("", 0),
    卖萌("", 0),
    大小通吃("", 0),
    山崩("", 0),
    幻梦之击("", 0),
    技能格挡("", 0),
    攻击提升("", 0),
    战争咆哮("", 0),
    月之守望("", 0),
    樱花动漫("", 0),
    毒伤("", 0),
    灵魂禁封("", 0),
    炽焰("", 0),
    物理格挡("", 0),
    狂暴("", 0),
    群体不动("", 0),
    群体冰甲("", 0),
    群体反击("", 0),
    群体反射("", 0),
    群体吸血("", 0),
    群体回春术("", 0),
    群体弱点("", 0),
    群体战意("", 0),
    群体暴击("", 0),
    群体水流护甲("", 0),
    群体狙击("", 0),
    群体盾刺("", 0),
    群体穿刺("", 0),
    群体转生("", 0),
    群体闪避("", 0),
    群体魔甲("", 0),
    联防("", 0),
    铁壁方阵("", 0),
    霜火之怒("", 0),
    连锁炸弹("", 0),
    血肉之舞("", 0),
    觉醒地狱同盟("", 0),
    觉醒森林同盟("", 0),
    觉醒王国同盟("", 0),
    觉醒蛮荒同盟("", 0),
    自毁("", 0),
    魔咒之骸("", 0),

    // [降临]封印
    万蛛之后("", 0),
    三个首都("", 0),
    三姐的新年贺词("", 0),
    丰盛的晚餐("", 0),
    二姐的新年贺词("", 0),
    五星荣耀("", 0),
    众神之神("", 0),
    傲慢惩罚者("", 0),
    光灿侍女("", 0),
    兽牙("", 0),
    兽骨("", 0),
    冰霜巨人("", 0),
    华伦收藏家("", 0),
    可爱的小南瓜("", 0),
    啤酒加香肠("", 0),
    善良("", 0),
    噩梦之主("", 0),
    四妹的新年贺词("", 0),
    圣诞彩虹使者("", 0),
    圣诞抚琴使者("", 0),
    圣诞狩猎使者("", 0),
    圣诞生命使者("", 0),
    圣诞美酒使者("", 0),
    圣诞铸铁使者("", 0),
    圣诞节快乐("", 0),
    复仇女神("", 0),
    大地之母("", 0),
    大姐的新年贺词("", 0),
    奔雷优雅骑士("", 0),
    奔雷伶俐歌姬("", 0),
    奔雷双斩利刃("", 0),
    奔雷唤潮之灵("", 0),
    奔雷天空射手("", 0),
    奔雷重击战狂("", 0),
    好望角之光("", 0),
    嫉妒惩罚者("", 0),
    富商钱袋("", 0),
    山姆大叔("", 0),
    年终回馈("", 0),
    德国老坦克("", 0),
    德意志精神("", 0),
    怠惰惩罚者("", 0),
    恶魔魔偶("", 0),
    情人节快乐("", 0),
    意外的收获("", 0),
    愤怒惩罚者("", 0),
    战斗魔偶("", 0),
    新年快乐("", 0),
    新春姐妹花乐("", 0),
    新春姐妹花年("", 0),
    新春姐妹花快("", 0),
    新春姐妹花新("", 0),
    新春小羊("", 0),
    星星的你("", 0),
    晚会之星公主("", 0),
    晚会之星女郎("", 0),
    晚会之星恶魔("", 0),
    晚会之星精灵("", 0),
    晚会之星鬼魂("", 0),
    晚会之星魔女("", 0),
    晨星之子("", 0),
    智慧("", 0),
    暗夜唤灵法师("", 0),
    暗夜死灵术士("", 0),
    暗夜皎月女神("", 0),
    暗夜阴影佣兵("", 0),
    暗夜鬼魅刺客("", 0),
    暗夜魔剑骑士("", 0),
    暴食惩罚者("", 0),

    极秘("", 0),
    梦境侍女("", 0),
    樱魂冠冕圣女("", 0),
    樱魂律法圣女("", 0),
    樱魂心控圣女("", 0),
    樱魂战神圣女("", 0),
    樱魂灵力圣女("", 0),
    樱魂诡诈圣女("", 0),
    歌姬魔偶("", 0),
    武斗侍女("", 0),
    毁灭之神("", 0),

    治愈侍女("", 0),
    深海魔偶("", 0),
    深渊影魔("", 0),
    灵气("", 0),

    狂欢桑巴("", 0),
    猫猫神典雅("", 0),
    猫猫神勇气("", 0),
    猫猫神善良("", 0),
    猫猫神妩媚("", 0),
    猫猫神胆小("", 0),
    猫猫神自信("", 0),
    玉女("", 0),
    生命侍女("", 0),
    白金回馈("", 0),
    白金智者("", 0),
    白银学者("", 0),
    皇城金库("", 0),
    神圣("", 0),
    秘术转换("", 0),

    竞技之王("", 0),
    童话侍女("", 0),
    精灵之兔仙("", 0),
    精灵之狐仙("", 0),
    精灵之百花("", 0),
    精灵之若水("", 0),
    精灵之蛇女("", 0),
    精灵之音痴("", 0),
    纯洁("", 0),

    美式橄榄("", 0),
    美食的诱惑("", 0),


    胜利的纪念("", 0),

    自由国度("", 0),
    舌战群儒("", 0),
    蛮皇女帝("", 0),

    行者魔偶("", 0),

    财富魔偶("", 0),
    贪婪惩罚者("", 0),

    足球王国("", 0),

    遗迹宝藏("", 0),
    邪龙之神("", 0),
    金童("", 0),
    钻石大神("", 0),
    钻石王国("", 0),

    青铜新人("", 0),
    飞火流星("", 0),
    马其诺防线("", 0),
    马赛回旋舞("", 0),
    骑士咒术骑师("", 0),
    骑士圣戟神女("", 0),
    骑士暗夜妖剑("", 0),
    骑士海力大神("", 0),
    骑士海王之女("", 0),
    骑士火焰智者("", 0),
    骑士信仰("", 0),
    高卢雄鸡("", 0),
    高贵("", 0),
    魔化万能卡牌("", 0),
    魔卡王者("", 0),

    黄金战士("", 0),
    齐天小猴王("", 0);

    private String wikiId;
    private int initImpact;
    private int incrImpact;
    private int initImpact2;
    private int incrImpact2;
    private int[] impact3;
    private HashSet <SkillTag> tags;
    private SkillType attachedType1;
    private int attachedLevel1;
    private SkillType attachedType2;
    private int attachedLevel2;

    SkillType(String wikiId, int[] impact3, SkillTag ... tags) {
        this(wikiId, 0, 0, impact3, tags);
    }

    SkillType(String wikiId, int incrImpact, SkillTag ... tags) {
        this(wikiId, 0, incrImpact, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, 0, 0, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int[] impact3, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, 0, 0, impact3, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int initImpact2, int incrImpact2, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, initImpact2, incrImpact2, null, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int initImpact2, int incrImpact2, int[] impact3, SkillTag ... tags) {
        this(wikiId, initImpact, incrImpact, initImpact2, incrImpact2, impact3, null, -1, null, -1, tags);
    }

    SkillType(String wikiId, SkillType attachedType1, SkillType attachedType2, SkillTag ... tags) {
        this(wikiId, 0, 0, 0, 0, null, attachedType1, -1, attachedType2, -1, tags);
    }

    SkillType(String wikiId, SkillType attachedType1, int attachedLevel1, SkillType attachedType2, int attachedLevel2, SkillTag ... tags) {
        this(wikiId, 0, 0, 0, 0, null, attachedType1, attachedLevel1, attachedType2, attachedLevel2, tags);
    }

    SkillType(String wikiId, int initImpact, int incrImpact, int initImpact2, int incrImpact2, int[] impact3,
              SkillType attachedType1, int attachedLevel1, SkillType attachedType2, int attachedLevel2, SkillTag ... tags) {
        this.wikiId = wikiId;
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
        this.initImpact2 = initImpact2;
        this.incrImpact2 = incrImpact2;
        if (impact3 != null) {
            this.impact3 = Arrays.copyOf(impact3, impact3.length);
        }
        this.attachedType1 = attachedType1;
        this.attachedLevel1 = attachedLevel1;
        this.attachedType2 = attachedType2;
        this.attachedLevel2 = attachedLevel2;
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
        if (this.impact3 == null || level < 0) {
            return 0;
        }
        if (level >= this.impact3.length) {
            return this.impact3[this.impact3.length];
        }
        return this.impact3[level];
    }

    public boolean containsTag(SkillTag tag) {
        return this.tags.contains(tag);
    }

    public boolean isGrowable() {
        return this.impact3 != null || this.incrImpact != 0 || this.initImpact != 0;
    }

    public SkillType getAttachedType1() {
        return attachedType1;
    }

    public int getAttachedLevel1() {
        return attachedLevel1;
    }

    public SkillType getAttachedType2() {
        return attachedType2;
    }

    public int getAttachedLevel2() {
        return attachedLevel2;
    }

}