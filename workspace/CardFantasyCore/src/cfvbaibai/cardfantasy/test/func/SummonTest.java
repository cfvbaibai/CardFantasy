package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;

public class SummonTest extends FeatureValidationTest {
    /**
     * 召唤王国战士基本测试
     */
    @Test
    public void test召唤王国战士() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "陨星魔法使", "占位符*3");
        context.addToField(0, 0);
        CardInfo c1 = context.addToField(1, 1);
        CardInfo c2 = context.addToField(2, 1);
        CardInfo c3 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(3, context.getPlayer(0).getField().size());
        Assert.assertEquals(745, 5000 - c1.getHP());
        Assert.assertEquals(0, 5000 - c2.getHP()); // 被召唤的卡在被召唤的那一轮不能攻击
        Assert.assertEquals(0, 5000 - c3.getHP());

        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(745 + 745, 5000 - c1.getHP());
        Assert.assertEquals(395, 5000 - c2.getHP());
        Assert.assertEquals(225, 5000 - c3.getHP());
    }
    
    /**
     * 被召唤的卡死亡时，处理离场效果，不进入墓地
     */
    @Test
    public void test召唤王国战士_被召唤卡死亡() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "陨星魔法使", "占位符", "金属巨龙");
        CardInfo c陨星魔法师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(3, context.getPlayer(0).getField().size());
        Assert.assertEquals(1850 + 200 /* 召唤圣骑士的王国守护 */, c陨星魔法师.getHP());

        random.addNextNumbers(0); // 金属巨龙暴击成功
        context.proceedOneRound();
        Assert.assertEquals(1850, c陨星魔法师.getHP()); // 圣骑士离场，失去BUFF

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size()); // 召唤的两张卡死了一张，此时仍不能召唤新卡
        Assert.assertEquals(0, context.getPlayer(0).getGrave().size()); // 被召唤的卡死亡后不进入墓地
    }
    
    @Test
    public void test召唤王国战士_送还() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "陨星魔法使", "占位符", "降临天使");
        context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(0, context.getPlayer(0).getDeck().size()); // 被召唤的卡被送还后不进入卡组
    }

    @Test
    public void test召唤王国战士_再召唤() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "陨星魔法使*2", "占位符*2", "金属巨龙", "占位符", "金属巨龙");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 1);
        context.addToField(3, 1);
        CardInfo c金属巨龙1 = context.addToField(4, 1);
        context.addToField(5, 1);
        CardInfo c金属巨龙2 = context.addToField(6, 1);
        context.startGame();

        Field fieldA = context.getPlayer(0).getField();
        context.proceedOneRound();
        Assert.assertEquals(6, fieldA.size()); // 两个陨星魔法使各召唤2张卡

        random.addNextNumbers(0, 0); // 两个金属巨龙都暴击成功, 每个陨星魔法使都被杀死一张仆从
        context.proceedOneRound();
        Assert.assertEquals(4, fieldA.size());

        c金属巨龙1.reset(); // 回血免得被打死
        c金属巨龙2.reset(); // 回血免得被打死
        context.proceedOneRound();
        Assert.assertEquals(4, fieldA.size()); // 此时任何一张陨星魔法使都不能再召唤

        random.addNextNumbers(0); // 金属巨龙1暴击，杀死陨星魔法使1的仆从，金属巨龙2直接攻击英雄
        context.proceedOneRound();
        Assert.assertEquals(3, fieldA.size());

        c金属巨龙1.reset(); // 回血免得被打死
        c金属巨龙2.reset(); // 回血免得被打死
        context.proceedOneRound();
        Assert.assertEquals(5, fieldA.size()); // 陨星魔法使1的仆从全灭，可以再次召唤
    }
}
