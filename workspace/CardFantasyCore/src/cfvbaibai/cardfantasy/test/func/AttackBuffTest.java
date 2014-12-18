package cfvbaibai.cardfantasy.test.func;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class AttackBuffTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

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
}
