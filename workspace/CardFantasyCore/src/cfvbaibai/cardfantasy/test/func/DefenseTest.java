package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class DefenseTest extends FeatureValidationTest {
    /**
     * 多重冰甲效果只取最强力的那个
     */
    @Test
    public void test冰封_冰甲() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "金属巨龙-1", "战斗猛犸象", "冰封");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象 = context.addToField(1, 1);
        RuneInfo r冰封 = context.addToRune(0, 1);
        context.startGame();

        r冰封.activate();
        random.addNextNumbers(1000); // 金属巨龙不暴击
        context.proceedOneRound();

        Assert.assertEquals(100 /* 猛犸象的冰甲7被冰封的冰甲9覆盖 */, 1220 - c战斗猛犸象.getHP());
    }
    
    /**
     * 格挡先于冰封结算
     */
    @Test
    public void test格挡_冰封() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "秘银巨石像", "金属巨龙", "冰封");
        context.addToField(0, 0);
        CardInfo c金属巨龙 = context.addToField(1, 1);
        RuneInfo r冰封 = context.addToRune(0, 1);
        context.startGame();

        r冰封.activate();
        context.proceedOneRound();

        Assert.assertEquals(100 /* 金属巨龙的格挡8先结算，再结算冰封的冰甲9 */, 1710 - c金属巨龙.getHP());
    }

    /**
     * 岩壁后于冰甲结算
     */
    @Test
    public void test岩壁_冰甲() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "秘银巨石像", "凤凰+冰甲1", "岩壁-1");
        context.addToField(0, 0);
        CardInfo c冰甲凤凰 = context.addToField(1, 1);
        RuneInfo r岩壁 = context.addToRune(0, 1);
        context.startGame();

        r岩壁.activate();
        context.proceedOneRound();

        Assert.assertEquals(180 - 120 /* 猛犸的冰甲先结算，然后结算岩壁 */, 1690 - c冰甲凤凰.getHP());
    }
    
    /**
     * 横扫为溅射
     */
    @Test
    public void test横扫_格挡() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "光明之龙", "牛头人酋长*2");
        context.addToField(0, 0);
        CardInfo c牛头人酋长1 = context.addToField(1, 1);
        CardInfo c牛头人酋长2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(630 - 140 /* 格挡 */, 1050 - c牛头人酋长1.getHP());
        Assert.assertEquals((630 - 140) /* 先算溅射伤害 */ - 140 /* 2号位酋长的格挡 */, 1050 - c牛头人酋长2.getHP());
    }
    
    /**
     * 横扫为溅射
     */
    @Test
    public void test横扫_冰甲() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "光明之龙", "战斗猛犸象", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象 = context.addToField(1, 1);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(120 /* 冰甲 */, 1220 - c战斗猛犸象.getHP());
        Assert.assertEquals(120 /* 溅射 */, 1400 - c秘银巨石像.getHP());
    }
    
    /**
     * 连锁攻击能够被格挡
     */
    @Test
    public void test连锁攻击_格挡() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "麒麟兽", "牛头人酋长*2");
        context.addToField(0, 0);
        CardInfo c牛头人酋长1 = context.addToField(1, 1);
        CardInfo c牛头人酋长2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(610 - 140 /* 格挡 */, 1050 - c牛头人酋长1.getHP());
        Assert.assertEquals(610 * 175 / 100 - 140 /* 格挡 */, 1050 - c牛头人酋长2.getHP());
    }
    
    /**
     * 连锁攻击能够被冰甲阻挡
     */
    @Test
    public void test连锁攻击_冰甲() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "麒麟兽", "战斗猛犸象*2");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象1 = context.addToField(1, 1);
        CardInfo c战斗猛犸象2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(120 /* 冰甲 */, 1220 - c战斗猛犸象1.getHP());
        Assert.assertEquals(120 /* 冰甲 */, 1220 - c战斗猛犸象2.getHP());
    }
}
