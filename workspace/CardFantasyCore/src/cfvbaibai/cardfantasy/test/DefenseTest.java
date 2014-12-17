package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class DefenseTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    /**
     * 多重冰甲效果只取最强力的那个
     */
    @Test
    public void test冰封_冰甲() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "金属巨龙-1", "战斗猛犸象", "冰封");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象 = context.addToField(1, 1);
        RuneInfo r冰封 = context.addToRune(0, 1);
        context.getStage().gameStarted();

        r冰封.activate();
        random.addNextNumbers(1000); // 金属巨龙不暴击
        context.getEngine().proceedOneRound();

        Assert.assertEquals(100 /* 猛犸象的冰甲7被冰封的冰甲9覆盖 */, 1220 - c战斗猛犸象.getHP());
    }
}
