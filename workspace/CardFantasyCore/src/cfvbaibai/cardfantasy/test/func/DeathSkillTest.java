package cfvbaibai.cardfantasy.test.func;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class DeathSkillTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }
    
    /**
     * 法力反射无法防御自爆
     */
    @Test
    public void test法力反射_自爆() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "元素灵龙", "哥布林术士");
        CardInfo c元素灵龙 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 哥布林术士自爆
        context.proceedOneRound();

        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(120, 1480 - c元素灵龙.getHP());
    }
}
