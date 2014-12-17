package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class HealingTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    @Test
    public void test治疗之雾_三卡治疗() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "秘银巨石像*4", "东方幻术师");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c东方幻术师 = context.addToField(4, 0).setBasicHP(100);
        CardInfo c秘银巨石像3 = context.addToField(2, 0).setBasicHP(100);
        CardInfo c秘银巨石像4 = context.addToField(3, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(100, c秘银巨石像1.getHP());
        Assert.assertEquals(300, c秘银巨石像2.getHP());
        Assert.assertEquals(300, c东方幻术师.getHP());
        Assert.assertEquals(300, c秘银巨石像3.getHP());
        Assert.assertEquals(100, c秘银巨石像4.getHP());
    }

    @Test
    public void test治疗之雾_两卡治疗_左面() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "秘银巨石像*2", "东方幻术师");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c东方幻术师 = context.addToField(2, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(100, c秘银巨石像1.getHP());
        Assert.assertEquals(300, c秘银巨石像2.getHP());
        Assert.assertEquals(300, c东方幻术师.getHP());
    }
    
    @Test
    public void test治疗之雾_两卡治疗_右面() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "东方幻术师", "秘银巨石像*2");
        CardInfo c东方幻术师 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c秘银巨石像1 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(2, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(300, c东方幻术师.getHP());
        Assert.assertEquals(300, c秘银巨石像1.getHP());
        Assert.assertEquals(100, c秘银巨石像2.getHP());
    }
    
    @Test
    public void test治疗之雾_单卡治疗() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "东方幻术师");
        CardInfo c东方幻术师 = context.addToField(0, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(300, c东方幻术师.getHP());
    }

    @Test
    public void test治疗之雾_部分治疗() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "秘银巨石像", "东方幻术师", "秘银巨石像");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(1400);
        CardInfo c东方幻术师 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(2, 0).setBasicHP(1300);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(1400, c秘银巨石像1.getHP());
        Assert.assertEquals(300, c东方幻术师.getHP());
        Assert.assertEquals(1400, c秘银巨石像2.getHP());
    }
}
