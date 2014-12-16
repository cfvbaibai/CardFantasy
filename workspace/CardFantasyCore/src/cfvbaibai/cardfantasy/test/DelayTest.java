package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;

public class DelayTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }
    
    @Test
    public void test阻碍_奇数() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "死兆星", "淘气灯灵");
        context.addToField(0, 0);
        CardInfo c淘气灯灵 = context.addToHand(1, 1);
        Field fieldB = context.getPlayer(1).getField();
        context.getStage().gameStarted();

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(1, c淘气灯灵.getSummonDelay());

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c淘气灯灵.getSummonDelay());

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c淘气灯灵.getSummonDelay());

        random.addNextPicks(0).addNextNumbers(0);
        context.getEngine().proceedOneRound();
        Assert.assertEquals(1, fieldB.size());
    }
    
    @Test
    public void test阻碍_偶数() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "死兆星", "堕落精灵");
        context.addToField(0, 0);
        CardInfo c堕落精灵 = context.addToHand(1, 1);
        Field fieldB = context.getPlayer(1).getField();
        context.getStage().gameStarted();

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(2, c堕落精灵.getSummonDelay());

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(1, c堕落精灵.getSummonDelay());

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(1, c堕落精灵.getSummonDelay());

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        context.getEngine().proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        random.addNextNumbers(0, 0);
        context.getEngine().proceedOneRound();
        Assert.assertEquals(1, fieldB.size());
    }
}