package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public class AttackBuffTest extends SkillValidationTest {
    /**
     * 暴击量按照嗜血后的攻击力算
     */
    @Test
    public void test嗜血_暴击() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "犀牛人武士", "凤凰+格挡10");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "洪荒巨熊", "秘银巨石像");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "隐世先知+弱点攻击", "末日预言师", "时空旅者");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "东方幻术师", "远古海妖-5", "淬炼");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "东方幻术师", "末日预言师", "占位符", "秘银巨石像");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像", "远古海妖-5", "淬炼");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "隐世先知+弱点攻击", "末日预言师", "淬炼");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "纯洁圣女", "降临天使*2");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "怒雪咆哮+穷追猛打1", "占位符");
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
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "堕落精灵法师+穷追猛打1", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 堕落精灵法师霜冻新星冻结成功
        context.proceedOneRound();
        Assert.assertEquals(40 /* 霜冻新型2 */ + 445 * 115 / 100 /* 穷追猛打 */, 5000 - c占位符.getHP());
    }
}
