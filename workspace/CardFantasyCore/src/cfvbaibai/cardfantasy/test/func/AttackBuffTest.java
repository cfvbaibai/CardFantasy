package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public class AttackBuffTest extends FeatureValidationTest {
    @Test
    public void test嗜血_暴击() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "犀牛人武士", "凤凰+格挡10");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.getStage().gameStarted();
        
        random.addNextNumbers(1000); // 犀牛人武士不暴击
        context.getEngine().proceedOneRound();
        
        context.getStage().setActivePlayerNumber(0);
        random.addNextNumbers(0); // 犀牛人武士暴击
        context.getEngine().proceedOneRound();
        
        int expectedDamage = 485 - 200 /* 第一回合 */ + (485 + 40) * 240 / 100 - 200 /* 第二回合 */;
        int actualDamage = 1690 - c凤凰.getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }

    @Test
    public void test狂热_暴击() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "洪荒巨熊", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c秘银 = context.addToField(1, 1);
        context.getStage().gameStarted();

        context.getStage().setActivePlayerNumber(1);
        context.getEngine().proceedOneRound();

        random.addNextNumbers(0); // 洪荒巨熊暴击
        context.getEngine().proceedOneRound();

        int expectedDamage = (480 + 60) * 2 /* 第二回合 */;
        int actualDamage = 1400 - c秘银.getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }

    /**
     * 攻击力计算方式：(基础 + 邪灵汲取 + 淬炼) * 暴击 - (基础 + 邪灵汲取 + 淬炼) * 虚弱
     * @throws HeroDieSignal 
     */
    @Test
    public void test邪灵汲取_淬炼_暴击_虚弱() throws HeroDieSignal {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "东方幻术师", "远古海妖", "淬炼");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToRune(0, 1);
        context.getStage().gameStarted();

        random.addNextPicks(0); // 东方幻术师虚弱远古海妖
        random.addNextPicks(0).addNextNumbers(1000);  // 东方幻术师迷魂远古海妖失败
        context.getEngine().proceedOneRound();

        context.getPlayer(1).setHP(1); // 强行启动淬炼
        random.addNextPicks(0); // 淬炼
        random.addNextNumbers(0); // 远古海妖暴击
        context.getEngine().proceedOneRound();
        int baseAT = 625 /* 基础攻击力 */ + 380 * 15 / 100 /* 邪灵汲取 */ + 120 /* 淬炼 */;
        Assert.assertEquals(baseAT * 2 /* 暴击 */ - baseAT / 2 /* 虚弱 */, 1290 - c东方幻术师.getHP());
    }
}
