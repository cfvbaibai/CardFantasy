package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class AttackBuffTest extends SkillValidationTest {
    /**
     * 暴击量按照嗜血后的攻击力算
     */
    @Test
    public void test嗜血_暴击() {
        SkillTestContext context = prepare(50, 50, "犀牛人武士", "凤凰+格挡10");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(1000); // 犀牛人武士不暴击
        context.proceedOneRound();

        context.getStage().setActivePlayerNumber(0);
        random.addNextNumbers(0); // 犀牛人武士暴击
        context.proceedOneRound();
        
        int expectedDamage = 485 - 200 /* 第一回合 */ + (485 + 40) * 240 / 100 - 200 /* 第二回合 */;
        int actualDamage = 1690 - c凤凰.getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }

    /**
     * 暴击量按照狂热后的攻击力算
     */
    @Test
    public void test狂热_暴击() {
        SkillTestContext context = prepare(50, 50, "洪荒巨熊", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c秘银 = context.addToField(1, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        context.proceedOneRound();

        random.addNextNumbers(0); // 洪荒巨熊暴击
        context.proceedOneRound();

        int expectedDamage = (480 + 60) * 2 /* 第二回合 */;
        int actualDamage = 1400 - c秘银.getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }

    /**
     * 邪灵汲取的量按照被削弱后的计算
     */
    @Test
    public void test邪灵汲取_削弱() {
        SkillTestContext context = prepare(50, 50, "隐世先知+弱点攻击", "末日预言师", "时空旅者");
        CardInfo c隐世先知 = context.addToField(0, 0);
        CardInfo c末日预言师 = context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        int atDecreased = 840 * 18 / 100;
        Assert.assertEquals(840 - atDecreased, c隐世先知.getCurrentAT());
        Assert.assertEquals(635 + atDecreased, c末日预言师.getCurrentAT());
        Assert.assertEquals(840, 1770 - c末日预言师.getHP());

        int at隐世先知RoundStart = c隐世先知.getCurrentAT();
        int at末日预言师RoundStart = c末日预言师.getCurrentAT();
        int hp隐世先知RoundStart = c隐世先知.getHP();
        random.addNextPicks(0); // 时空旅者群体削弱
        context.proceedOneRound();
        Assert.assertEquals(at隐世先知RoundStart - 40, c隐世先知.getCurrentAT());
        Assert.assertEquals(at末日预言师RoundStart, hp隐世先知RoundStart - c隐世先知.getHP());

        at隐世先知RoundStart = c隐世先知.getCurrentAT();
        at末日预言师RoundStart = c末日预言师.getCurrentAT();
        int hp末日预言师RoundStart = c末日预言师.getHP();
        atDecreased = at隐世先知RoundStart * 18 / 100;
        context.proceedOneRound();
        Assert.assertEquals(at隐世先知RoundStart - atDecreased, c隐世先知.getCurrentAT());
        Assert.assertEquals(at末日预言师RoundStart + atDecreased, c末日预言师.getCurrentAT());
        Assert.assertEquals(at隐世先知RoundStart, hp末日预言师RoundStart - c末日预言师.getHP());
    }

    /**
     * 攻击力计算方式：(基础 + 邪灵汲取 + 淬炼) * 暴击 - (基础 + 邪灵汲取 + 淬炼) * 虚弱
     * @throws HeroDieSignal 
     */
    @Test
    public void test邪灵汲取_淬炼_暴击_虚弱() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "东方幻术师", "远古海妖-5", "淬炼");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        CardInfo c远古海妖 = context.addToField(1, 1);
        context.addToRune(0, 1);
        context.startGame();

        random.addNextPicks(0); // 东方幻术师虚弱远古海妖
        random.addNextPicks(0).addNextNumbers(1000);  // 东方幻术师迷魂远古海妖失败
        context.proceedOneRound();
        int impact邪灵汲取 = 380 * 15 / 100;
        Assert.assertEquals(475 + impact邪灵汲取, c远古海妖.getCurrentAT());

        context.getPlayer(1).setHP(1); // 强行启动淬炼
        random.addNextPicks(0); // 淬炼
        random.addNextNumbers(0); // 远古海妖暴击
        context.proceedOneRound();
        int baseAT = 475 /* 基础攻击力 */ + impact邪灵汲取 + 120 /* 淬炼 */;
        Assert.assertEquals(baseAT * 2 /* 暴击 */ - baseAT / 2 /* 虚弱 */, 1290 - c东方幻术师.getHP());
        Assert.assertEquals(baseAT, c远古海妖.getCurrentAT());
    }

    /**
     * 被虚弱的同时被邪灵汲取的话，邪灵汲取的量按照未虚弱时计算
     */
    @Test
    public void test邪灵汲取_种族克制_虚弱() {
        SkillTestContext context = prepare(50, 50, "东方幻术师", "末日预言师", "占位符", "秘银巨石像");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 1);
        CardInfo c秘银巨石像 = context.addToField(3, 1);
        context.startGame();

        random.addNextPicks(0, 0).addNextNumbers(1000); // 东方幻术师先虚弱和迷魂占位符，并且迷魂失败
        context.proceedOneRound();

        context.proceedOneRound(); // 此轮秘银巨石像攻击了末日预言师被邪灵汲取了
        int c秘银巨石像AT = 660 - 660 * 18 / 100 /* 邪灵汲取18% */;
        Assert.assertEquals(c秘银巨石像AT, c秘银巨石像.getCurrentAT());

        random.addNextPicks(1, 1).addNextNumbers(0); // 东方幻术师虚弱并迷魂秘银巨石像成功
        context.proceedOneRound();

        int heroHpB = context.getPlayer(1).getHP(); // 先记录英雄血量
        context.proceedOneRound();  // 秘银巨石像以一半攻击力攻击自己英雄
        Assert.assertEquals(c秘银巨石像AT / 2, heroHpB - context.getPlayer(1).getHP());
    }

    /**
     * 淬炼增加的攻击力可以抵消邪灵汲取降低的攻击力
     */
    @Test
    public void test邪灵汲取_淬炼_完全抵消() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "远古海妖-5", "淬炼");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c远古海妖 = context.addToField(1, 1);
        context.addToRune(0, 0);
        context.startGame();

        context.getPlayer(0).setHP(1);      // 强行启动淬炼
        random.addNextPicks(0);             // 淬炼取对象
        context.proceedOneRound();
        int atDecreased = (660 + 120) * 15 / 100;   // 邪灵汲取15%, 117
        Assert.assertEquals(117, atDecreased);
        Assert.assertEquals(660 + 120, 1550 - c远古海妖.getHP());
        Assert.assertEquals(475 + atDecreased, c远古海妖.getCurrentAT());

        context.getPlayer(0).setHP(10000);  // 强行结束淬炼
        random.addNextNumbers(1000);        // 远古海妖暴击失败
        context.proceedOneRound();

        // 又过一轮，淬炼结束
        int at远古海妖roundStart = 475 + atDecreased;
        // 降低的攻击力中可以被淬炼增加的攻击力部分, 但如果已经完全抵消，溢出部分无法留存
        // 邪灵汲取量117，淬炼增加120，所以秘银巨石像攻击力不变
        int at秘银巨石像roundStart = 660 - Math.max(0, atDecreased - 120);
        Assert.assertEquals(660, at秘银巨石像roundStart); // 应该完全抵消
        context.proceedOneRound();
        atDecreased = at秘银巨石像roundStart * 15 / 100;
        Assert.assertEquals(at秘银巨石像roundStart - atDecreased, c秘银巨石像.getCurrentAT());
        Assert.assertEquals(at远古海妖roundStart + atDecreased, c远古海妖.getCurrentAT());
    }

    /**
     * 淬炼增加的攻击力可以抵消邪灵汲取降低的攻击力
     */
    @Test
    public void test邪灵汲取_淬炼_部分抵消() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "隐世先知+弱点攻击", "末日预言师", "淬炼");
        CardInfo c隐世先知 = context.addToField(0, 0);
        CardInfo c末日预言师 = context.addToField(1, 1);
        context.addToRune(0, 0);
        context.startGame();

        context.getPlayer(0).setHP(1);      // 强行启动淬炼
        random.addNextPicks(0);             // 淬炼取对象
        context.proceedOneRound();
        int atDecreased = (840 + 120) * 18 / 100;   // 邪灵汲取18%
        Assert.assertEquals(840 + 120, 1770 - c末日预言师.getHP());
        Assert.assertEquals(635 + atDecreased, c末日预言师.getCurrentAT());

        context.getPlayer(0).setHP(10000);  // 强行结束淬炼
        random.addNextNumbers(1000);        // 末日预言师暴击失败
        context.proceedOneRound();
        Assert.assertEquals(635 + atDecreased, 1725 - c隐世先知.getHP());

        // 又过一轮，淬炼结束
        int at末日预言师roundStart = 635 + atDecreased;
        int hp末日预言师roundStart = 1770 - (840 + 120);
        int at隐世先知roundStart = 840 - (atDecreased - 120); /* 降低的攻击力中可以被淬炼增加的攻击力部分 */
        context.proceedOneRound();
        atDecreased = at隐世先知roundStart * 18 / 100;
        Assert.assertEquals(at隐世先知roundStart - atDecreased, c隐世先知.getCurrentAT());
        Assert.assertEquals(at末日预言师roundStart + atDecreased, c末日预言师.getCurrentAT());
        Assert.assertEquals(at隐世先知roundStart, hp末日预言师roundStart - c末日预言师.getHP());
    }

    /**
     * 打正对面的卡时还不增加攻击力，攻击力BUFF仅其它被连锁的对象起效
     */
    @Test
    public void test连锁攻击() {
        SkillTestContext context = prepare(50, 50, "纯洁圣女", "降临天使*2");
        context.addToField(0, 0);
        CardInfo c降临天使1 = context.addToField(1, 1);
        CardInfo c降临天使2 = context.addToField(2, 1);
        context.startGame();

        // 开始一轮让背刺先发动掉
        context.proceedOneRound();
        // 场地状态恢复
        context.getStage().setActivePlayerNumber(0);
        c降临天使1.reset();
        c降临天使2.reset();

        context.proceedOneRound();
        Assert.assertEquals(590, 2040 - c降临天使1.getHP());
        Assert.assertEquals(590, 2040 - c降临天使2.getHP());
    }

    /**
     * 寒霜冲击的冻结不触发穷追猛打
     */
    @Test
    public void test穷追猛打_寒霜冲击() {
        SkillTestContext context = prepare(50, 50, "怒雪咆哮+穷追猛打1", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 怒雪咆哮寒霜冲击冻结成功
        random.addNextPicks(0); // 怒雪咆哮战争怒吼
        context.proceedOneRound();
        Assert.assertEquals(780 + 165 /* 寒霜冲击8 */, 5000 - c占位符.getHP());
    }

    @Test
    public void test穷追猛打_冰弹冻结() {
        SkillTestContext context = prepare(50, 50, "堕落精灵法师+穷追猛打1", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 堕落精灵法师霜冻新星冻结成功
        context.proceedOneRound();
        Assert.assertEquals(40 /* 霜冻新型2 */ + 445 * 115 / 100 /* 穷追猛打 */, 5000 - c占位符.getHP());
    }

    /**
     * 凯撒之击享受种族之力加成
     */
    @Test
    public void test种族之力_凯撒之击() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+凯撒之击1", "秘银巨石像+王国之力10", "占位符");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals((810 + 250 /* 王国之力10 */) + (810 * 15 / 100 /* 凯撒之击1 */), 5000 - c占位符2.getHP());
    }

    @Test
    public void test凯撒之击_攻击英雄() {
        SkillTestContext context = prepare(
                50, 50, "秘银巨石像", "秘银巨石像+凯撒之击1", "秘银巨石像");
            context.addToField(0, 0);
            context.addToField(1, 0);
            context.addToField(2, 0);
            context.startGame();

            context.proceedOneRound();
            Assert.assertEquals(
                    810 + (660 * 15 / 100 /* 凯撒之击1 */) * 2 + 660 * 2 /* 两边秘银的直接攻击 */,
                    6390 - context.getPlayer(1).getHP());
    }

    /**
     * 迷魂中的卡无法发动凯撒之击
     */
    @Test
    public void test凯撒之击_迷魂_攻击英雄() {
        SkillTestContext context = prepare(
            50, 50, "占位符+迷魂10", "秘银巨石像", "秘银巨石像+凯撒之击1", "秘银巨石像");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c秘银巨石像2 = context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        random.addNextPicks(1).addNextNumbers(0); // 迷魂
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像2.getStatus().containsStatus(CardStatusType.迷惑));

        context.proceedOneRound();
        Assert.assertEquals(810, 6390 - context.getPlayer(1).getHP());
    }

    @Test
    public void test同调_普通() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+王国同调1", "秘银巨石像*2", "占位符");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToField(3, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(810 * 102 / 100, 5000 - c占位符.getHP());
    }
    
    /**
     * 同调不可以自我反馈
     */
    @Test
    public void test同调_自我反馈() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+王国同调1", "占位符");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符.getHP());
    }
    
    /**
     * 激发同调的卡下场后并不会使同调效果消失
     */
    @Test
    public void test同调_下场() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+王国同调1", "秘银巨石像*2", "占位符", "金属巨龙-15");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToField(3, 1);
        context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        random.addNextNumbers(0); // 金属巨龙暴击杀死秘银巨石像
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(810 * 102 / 100 * 2, 5000 - c占位符.getHP());
    }
    
    /**
     * 召唤物无法激发同调
     */
    @Test
    public void test同调_召唤物() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+森林同调1", "星夜女神", "占位符");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(810 * 101 / 100, 5000 - c占位符.getHP());
    }
    
    /**
     * 同调只能在同调卡上场后触发
     */
    @Test
    public void test同调_上场前() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "秘银巨石像+王国同调1", "占位符*2");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符2.getHP());
    }
    
    @Test
    public void test斩杀_基础() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    /*
     * 刚好一半HP无法斩杀
     */
    @Test
    public void test斩杀_刚好一半HP() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2500);
        context.startGame();

        context.proceedOneRound();
        Assert.assertFalse(c占位符.isDead());
        Assert.assertEquals(810, 2500 - c占位符.getHP());
    }

    /*
     * 免疫对斩杀无效
     */
    @Test
    public void test斩杀_免疫() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+免疫");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    /*
     * 格挡对斩杀无效
     */
    @Test
    public void test斩杀_格挡() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+格挡10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    @Test
    public void test斩杀_格挡_未触发() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+格挡10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(810 - 200 /* 格挡10 */, 5000 - c占位符.getHP());
    }

    /*
     * 岩壁对斩杀无效
     */
    @Test
    public void test斩杀_岩壁() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符", "岩壁-4");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        RuneInfo c岩壁 = context.addToRune(0, 1);
        context.startGame();

        c岩壁.activate();
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    /*
     * 冰甲对斩杀无效
     */
    @Test
    public void test斩杀_冰甲() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+冰甲10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }
    
    @Test
    public void test斩杀_冰甲_未触发() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+冰甲10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(90, 5000 - c占位符.getHP());
    }

    /*
     * 如果对方血量低于本方AT，仍然发动斩杀，破冰甲
     */
    @Test
    public void test斩杀_冰甲_血量低于攻击() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+冰甲10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(300);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    /*
     * 冰封对斩杀无效
     */
    @Test
    public void test斩杀_冰封() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符", "冰封-4");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        RuneInfo c冰封 = context.addToRune(0, 1);
        context.startGame();

        c冰封.activate();
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    /*
     * 闪避对斩杀有效
     */
    @Test
    public void test斩杀_闪避() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+闪避10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2499);
        context.startGame();

        random.addNextNumbers(0); // 占位符闪避成功
        context.proceedOneRound();
        Assert.assertFalse(c占位符.isDead());
        Assert.assertEquals(2499, c占位符.getHP());
    }

    /*
     * 致盲对斩杀有效
     */
    @Test
    public void test斩杀_致盲() {
        SkillTestContext context = prepare(50, 50, "占位符+致盲10", "秘银巨石像+斩杀");
        CardInfo c占位符 = context.addToField(0, 0).setBasicHP(2499);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 占位符对秘银巨石像使用致盲
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));

        random.addNextNumbers(0); // 占位符闪避成功
        context.proceedOneRound();
        Assert.assertFalse(c占位符.isDead());
        Assert.assertEquals(2499, c占位符.getHP());
    }

    /*
     * 斩杀造成的所有伤害都可转化为吸血量
     */
    @Test
    public void test斩杀_吸血() {
        SkillTestContext context = prepare(50, 50, "豺狼人刺客+斩杀", "占位符");
        CardInfo c豺狼人刺客 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
        Assert.assertEquals(40 /* 吸血4吸取40%伤害量 */ + 2, c豺狼人刺客.getHP());
    }

    /*
     * 斩杀对魔神无效
     */
    @Test
    public void test斩杀_魔神() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "迷魂邪龙之神");
        context.addToField(0, 0);
        CardInfo c邪龙之神 = context.addToField(1, 1).setBasicHP(1000);
        context.startGame();

        context.proceedOneRound();
        Assert.assertFalse(c邪龙之神.isDead());
        Assert.assertEquals(1000 - 810, c邪龙之神.getHP());
    }

    @Test
    public void test斩杀_种族之盾() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+蛮荒之盾10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(1000);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1000 * 35 / 100 /* 斩杀伤害被种族之盾减少 */, 1000 - c占位符.getHP());
    }

    @Test
    public void test神兵召唤_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+神兵召唤", "占位符");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0);
        context.proceedOneRound();
        
        Assert.assertEquals(500, 5000 - c占位符2.getHP());
        Assert.assertEquals(0, c占位符1.getCurrentAT());
    }

    @Test
    public void test觉醒神兵召唤_基础() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+觉醒神兵召唤", "金牌火鸡", "占位符", "秘银巨石像");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c金牌火鸡 = context.addToField(1, 0);
        CardInfo c占位符 = context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        random.addNextNumbers(0);
        context.proceedOneRound();

        // 神兵召唤被激活
        Assert.assertEquals(810 + 500, 5000 - c占位符.getHP());
        Assert.assertEquals(810, c秘银巨石像.getCurrentAT());

        context.proceedOneRound();
        // 此轮金牌火鸡被打死
        Assert.assertTrue(c金牌火鸡.isDead());

        random.addNextNumbers(0);
        context.proceedOneRound();
        // 由于神兵召唤已被激活，此轮虽然金牌火鸡已死，神兵召唤仍发挥效果
        Assert.assertEquals(810 + 500 + 810 + 500, 5000 - c占位符.getHP());
        Assert.assertEquals(810, c秘银巨石像.getCurrentAT());
    }

    @Test
    public void test觉醒神兵召唤_未觉醒() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+觉醒神兵召唤", "占位符");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(810, 5000 - c占位符.getHP());
        Assert.assertEquals(810, c秘银巨石像.getCurrentAT());
    }

    @Test
    public void test圣器召唤_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+圣器召唤", "占位符");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0);
        context.proceedOneRound();
        
        Assert.assertEquals(300, 5000 - c占位符2.getHP());
        Assert.assertEquals(0, c占位符1.getCurrentAT());
    }

    @Test
    public void test鬼王之怒_基础() {
        SkillTestContext context = prepare(50, 50, "魔剑士+鬼王之怒", "秘银巨石像-15");
        CardInfo c魔剑士 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        
        Assert.assertEquals(275 * 235 / 100, 1550 - c秘银巨石像.getHP());
        Assert.assertFalse(c魔剑士.isDead());
        Assert.assertFalse(c魔剑士.getStatus().containsStatus(CardStatusType.不屈));

        context.proceedOneRound();
        Assert.assertEquals(1, c魔剑士.getHP());
        Assert.assertTrue(c魔剑士.getStatus().containsStatus(CardStatusType.不屈));

        context.proceedOneRound();
        Assert.assertEquals(275 * 235 * 2 / 100, 1550 - c秘银巨石像.getHP());
        Assert.assertFalse(c魔剑士.isDead());
        Assert.assertFalse(c魔剑士.getStatus().containsStatus(CardStatusType.不屈));

        context.proceedOneRound();
        Assert.assertTrue(c魔剑士.isDead());
        Assert.assertFalse(c魔剑士.getStatus().containsStatus(CardStatusType.不屈));
    }
}
