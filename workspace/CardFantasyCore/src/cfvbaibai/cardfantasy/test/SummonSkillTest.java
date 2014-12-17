package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SummonSkillTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    /**
     * 降临技能在符文发动和结算之前发动
     */
    @Test
    public void test降临摧毁_焚天() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "独眼巨人", "焚天", "凤凰-5*2");
        context.addToHand(0, 0).setSummonDelay(0);
        RuneInfo r焚天 = context.addToRune(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 独眼巨人摧毁
        context.proceedOneRound();

        // 降临技能先结算，杀死一只凤凰，对方场上只剩一张森林卡，无法激活焚天
        Assert.assertFalse("焚天应该未激活", r焚天.isActivated());
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
    }
}
