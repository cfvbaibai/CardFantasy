package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class MultiAttackTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    /**
     * 被闪避的话就无法溅射
     */
    @Test
    public void test横扫_闪避() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "光明之龙", "大剑圣", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c大剑圣 = context.addToField(1, 1);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        context.startGame();

        random.addNextNumbers(0); // 大剑圣闪避成功
        context.proceedOneRound();

        Assert.assertEquals(0, 995 - c大剑圣.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());
    }
}
