package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class SpecialStatusTest {
    private static Randomizer.StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    @Test
    public void test冰冻_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "水源制造者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.getStage().gameStarted();

        random.addNextNumbers(0).addNextPicks(0); // 水源冰冻凤凰
        context.getEngine().proceedOneRound();

        context.getEngine().proceedOneRound();
        Assert.assertEquals(310 + 80, 1560 - c凤凰.getHP());
    }

    @Test
    public void test锁定_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "地岭拥有者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.getStage().gameStarted();

        random.addNextNumbers(0).addNextPicks(0); // 地岭拥有者锁定凤凰
        context.getEngine().proceedOneRound();

        context.getEngine().proceedOneRound();
        Assert.assertEquals(335, 1560 - c凤凰.getHP());
    }

    @Test
    public void test麻痹_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "风暴召唤者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.getStage().gameStarted();

        random.addNextNumbers(0).addNextPicks(0); // 风暴召唤者麻痹凤凰
        context.getEngine().proceedOneRound();

        random.addNextNumbers(0).addNextPicks(0); // 凤凰烈焰风暴
        context.getEngine().proceedOneRound();
        Assert.assertEquals(300 + 100 - 210, 1560 - c凤凰.getHP());
    }
}
