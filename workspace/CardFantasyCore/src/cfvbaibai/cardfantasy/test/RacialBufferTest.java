package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class RacialBufferTest {
    private static Randomizer.StaticRandomizer random;
    
    @BeforeClass
    public static void initialize() {
        random = new Randomizer.StaticRandomizer();
        Randomizer.registerRandomizer(random);
    }

    @After
    public void afterTest() {
        random.reset();
    }

    @Test
    public void test种族之力_暴击_背刺() {
        random.addNextNumbers(0); // 保证大剑圣的暴击

        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "隐世先知", "大剑圣", "凤凰*2");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 1).setSummonDelay(0);
        CardInfo c凤凰2 = context.addToHand(3, 1).setSummonDelay(0);

        context.getStage().gameStarted();
        int c凤凰2OriginalHP = c凤凰2.getHP();
        int expectedDamage = ((int)((560 /* Lv10 大剑圣 */ + 175 /* 王国之力 */) * 1.8 /* 暴击 */)) + 200 /* 背刺 */;
        context.getEngine().proceedOneRound();
        int actualDamage = c凤凰2OriginalHP - c凤凰2.getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }
    
    @Test
    public void test种族之力_削弱() {
        random.addNextPicks(0).addNextNumbers(0); // 保证独眼巨人摧毁隐世先知

        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "隐世先知-5", "大剑圣", "独眼巨人");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c大剑圣 = context.getPlayer(0).getHand().addCard(context.getCardInfo(1, 0)).setSummonDelay(0);
        context.addToHand(2, 1).setSummonDelay(0);

        context.getStage().gameStarted();
        context.getEngine().proceedOneRound();
        context.getEngine().proceedOneRound();

        // 削弱造成的DEBUFF会被种族之力抵消，种族之力消失后，原本攻击力不会下降
        Assert.assertEquals(560 /* Lv10 大剑圣 */, c大剑圣.getCurrentAT());
    }

    @Test
    public void test种族之力_瘟疫() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "隐世先知-5", "大剑圣", "木乃伊法老王", "奇美拉");

        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c大剑圣 = context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 1).setSummonDelay(0);
        
        context.getStage().gameStarted();
        random.addNextNumbers(1000); // 大剑圣不暴击
        context.getEngine().proceedOneRound();

        random.addNextNumbers(1000); // 大剑圣不闪避
        context.getEngine().proceedOneRound();

        random.addNextNumbers(1000); // 大剑圣不暴击
        context.getEngine().proceedOneRound();

        context.addToHand(3, 1).setSummonDelay(0);
        random.addNextPicks(0, 0); // 奇美拉摧毁隐世先知, 奇美拉攻击大剑圣火球
        random.addNextNumbers(1000, 1000);  // 大剑圣不闪避, 奇美拉火球最大伤害
        context.getEngine().proceedOneRound();

        Assert.assertEquals(560 - 25 * 2 /* Lv10 大剑圣受到两次瘟疫，每次25点 */, c大剑圣.getCurrentAT());
    }

    @Test
    public void test种族之力_种族克制() {
        random.addNextNumbers(1000); // 猎魔犬不暴击
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "猎魔犬", "隐世先知", "骨龙");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c骨龙 = context.addToField(2, 1);

        context.getStage().gameStarted();
        context.getEngine().proceedOneRound();

        Assert.assertEquals((int)((280 + 175 /* 王国之力 */) * 1.6 /* 圣光 */), 1250 - c骨龙.getHP());
    }
    
    @Test
    public void test种族克制_暴击() {
        random.addNextNumbers(0); // 猎魔犬暴击
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "猎魔犬", "骨龙");
        context.addToField(0, 0);
        CardInfo c骨龙 = context.addToField(1, 1);

        context.getStage().gameStarted();
        context.getEngine().proceedOneRound();

        Assert.assertEquals((280 * 100 + 280 * 60 /* 暴击 */ + 280 * 60 /* 圣光 */) / 100, 1250 - c骨龙.getHP());
    }
}
