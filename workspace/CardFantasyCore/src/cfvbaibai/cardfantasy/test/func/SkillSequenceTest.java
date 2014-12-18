package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class SkillSequenceTest extends FeatureValidationTest {
    /**
     * 卡牌有多个技能在同一时机下都可发动的话，按照牌面顺序依次发动
     */
    @Test
    public void test水源制造者_冰弹先于甘霖() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "水源制造者", "元素灵龙");
        context.addToField(0, 0).setBasicHP(100);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 水源制造者冰弹
        random.addNextPicks(0); // 水源制造者暴风雪（虽然不该发动）
        context.proceedOneRound();

        // 水源制造者只有100HP，发动冰弹被弹回收到120伤害，死亡。甘霖应该还没来得及发动。
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
    }

    /**
     * 卡牌有多个技能在同一时机下都可发动的话，按照牌面顺序依次发动
     */
    @Test
    public void test水源制造者_甘霖先于暴风雪() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "水源制造者", "元素灵龙");
        CardInfo c水源制造者 = context.addToField(0, 0).setBasicHP(130);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 水源制造者冰弹
        random.addNextPicks(0); // 水源制造者暴风雪
        context.proceedOneRound();

        // 水源制造者只有130HP，发动冰弹被弹回受到120伤害，还剩10HP。
        // 此时发动甘霖，恢复成160HP。再被发动暴风雪受到120伤害，剩40HP。
        // 若先发动暴风雪，就会死亡。
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(130 - 120 + 150 - 120, c水源制造者.getHP());
    }
}
