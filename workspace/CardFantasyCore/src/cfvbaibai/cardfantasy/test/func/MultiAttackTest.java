package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;


public class MultiAttackTest extends FeatureValidationTest {
    /**
     * 连锁攻击和横扫完全分开计算
     */
    @Test
    public void test连锁攻击_横扫() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "战场女武神", "占位符*4");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        CardInfo c占位符4 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(805, 5000 - c占位符1.getHP());
        Assert.assertEquals(805 + 805 * 175 / 100, 5000 - c占位符2.getHP());
        Assert.assertEquals(805 * 175 / 100, 5000 - c占位符3.getHP());
        Assert.assertEquals(805 * 175 / 100, 5000 - c占位符4.getHP());
    }
}
