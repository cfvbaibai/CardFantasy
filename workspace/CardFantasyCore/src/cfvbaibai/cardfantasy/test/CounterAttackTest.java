package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class CounterAttackTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    /**
     * 反击在吸血之后
     */
    @Test
    public void test反击_吸血() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "黑夜蝙蝠-1", "人马巡逻者");
        CardInfo c黑夜蝙蝠 = context.addToField(0, 0).setBasicHP(79);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(21, c黑夜蝙蝠.getHP());
    }
    
    /**
     * 闪避成功的话不触发雷盾
     */
    @Test
    public void test闪避_雷盾() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "秘银巨石像", "大剑圣", "雷盾");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c大剑圣 = context.addToField(1, 1);
        RuneInfo r雷盾 = context.addToRune(0, 1);
        context.startGame();
        
        r雷盾.activate();
        random.addNextNumbers(0); // 大剑圣闪避成功
        context.proceedOneRound();
        
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());
        Assert.assertEquals(0, 995 - c大剑圣.getHP());
    }
}
