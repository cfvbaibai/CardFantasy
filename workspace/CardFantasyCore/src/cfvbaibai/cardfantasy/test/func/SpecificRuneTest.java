package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SpecificRuneTest extends SkillValidationTest {
    /**
     * 玄石: 敌方场上卡多于我方2张时发动, 游戏里描述有误
     */
    @Test
    public void test玄石_激活() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符*5", "玄石");
        context.addToField(0, 0);
        RuneInfo r玄石 = context.addToRune(0, 0);
        context.startGame();

        // ROUND 1: 1 - 0
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 2: 1 - 1
        context.addToHand(1, 1).setSummonDelay(0);
        context.proceedOneRound();

        // ROUND 3: 1 - 1
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 4: 1 - 2
        context.addToHand(2, 1).setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 5: 1 - 2
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 6: 1 - 3
        context.addToHand(3, 1).setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertTrue(r玄石.isActivated());

        // ROUND 7: 1 - 3
        context.proceedOneRound();
        Assert.assertTrue(r玄石.isActivated());

        // ROUND 8: 2 - 3
        context.addToHand(4, 0).setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());
    }

    @Test
    public void test玄石_基本() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符*4", "玄石");
        CardInfo placeholder1 = context.addToHand(0, 0).setSummonDelay(6);
        CardInfo placeholder2 = context.addToHand(1, 0).setSummonDelay(6);
        context.addToField(2, 1);
        context.addToField(3, 1);
        RuneInfo r玄石 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(3, placeholder1.getSummonDelay());
        Assert.assertEquals(3, placeholder2.getSummonDelay());
        Assert.assertTrue(r玄石.isActivated());
        Assert.assertEquals(4, r玄石.getEnergy());
    }

    /**
     * 玄石的发动是在召唤之后, 所以虽然本轮可以把某张卡用玄石设到等待0，但还是不能召唤上场
     */
    @Test
    public void test玄石_激活顺序() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符*3", "玄石");
        CardInfo placeholder1 = context.addToHand(0, 0).setSummonDelay(2);
        context.addToField(1, 1);
        context.addToField(2, 1);
        RuneInfo r玄石 = context.addToRune(0, 0);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertTrue(r玄石.isActivated());
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertEquals(1, context.getPlayer(0).getHand().size());
        Assert.assertEquals(0, placeholder1.getSummonDelay());
    }
}
