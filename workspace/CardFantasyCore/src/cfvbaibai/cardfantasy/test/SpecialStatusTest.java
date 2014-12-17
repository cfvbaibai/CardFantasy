package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class SpecialStatusTest {
    private static StaticRandomizer random = FeatureValidationTests.getRandom();

    @After
    public void afterTest() {
        random.reset();
    }

    /**
     * 被冰冻时无法回春
     */
    @Test
    public void test冰冻_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "水源制造者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 水源冰冻凤凰
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(310 + 80, 1560 - c凤凰.getHP());
    }

    /**
     * 被锁定时无法回春
     */
    @Test
    public void test锁定_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "地岭拥有者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 地岭拥有者锁定凤凰
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(335, 1560 - c凤凰.getHP());
    }

    /**
     * 被麻痹时仍能回春
     */
    @Test
    public void test麻痹_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "风暴召唤者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 风暴召唤者麻痹凤凰
        context.proceedOneRound();

        random.addNextNumbers(0).addNextPicks(0); // 凤凰烈焰风暴
        context.proceedOneRound();
        Assert.assertEquals(300 + 100 - 210, 1560 - c凤凰.getHP());
    }
    
    /**
     * 冰冻+迷魂状态下，不可回春，但仍会攻击自己英雄
     */
    @Test
    public void test冰冻_迷魂_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "水源制造者-1", "彩翼公主", "凤凰");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c凤凰 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0);   // 水源制造者冰冻
        random.addNextPicks(0).addNextNumbers(0);   // 彩翼公主迷魂
        context.proceedOneRound();

        context.proceedOneRound();

        Assert.assertEquals(
            435 /* 彩翼公主的攻击 */ + 660 /* 凤凰被迷魂的攻击 */,
            6390 - context.getPlayer(1).getHP());
        Assert.assertEquals(80 /* 水源冰弹 */ + 218 /* 水源攻击 */ - 0 /* 无法回春 */, 1560 - c凤凰.getHP());
    }
    
    /**
     * 燃烧比回春先结算
     */
    @Test
    public void test燃烧_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "地狱红龙", "凤凰-5");
        context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(691);
        context.startGame();
        
        random.addNextPicks(0);     // 地狱红龙烈火焚神
        context.proceedOneRound();

        random.addNextPicks(0).addNextNumbers(0); // 凤凰烈焰风暴
        context.proceedOneRound();

        // 燃烧和回春结算前，凤凰HP还剩下: 691 - 540 - 150 = 1
        // 由于燃烧先结算，所以凤凰无法回春而死亡。
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
    }
    
    @Test
    public void test中毒_回春() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "蝎尾狮", "凤凰-5");
        context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(801);
        context.startGame();
        
        random.addNextPicks(0);     // 蝎尾狮毒液
        context.proceedOneRound();

        random.addNextPicks(0).addNextNumbers(0); // 凤凰烈焰风暴
        context.proceedOneRound();

        // 中毒和回春结算前，凤凰HP还剩下: 801 - 480 (攻击力) - 200 (背刺) - 120 (毒液) = 1
        // 由于中毒先结算，所以凤凰无法回春而死亡。
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
    }
}
