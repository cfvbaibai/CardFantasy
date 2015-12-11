package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;

public class MagicSkillTest extends SkillValidationTest {
    @Test
    public void test雷神降临_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+雷神降临8", "占位符*3");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        
        context.startGame();

        random.addNextPicks(0, 1, 2); // 雷神降临
        random.addNextNumbers(700, 760, 800); // 雷神降临

        context.proceedOneRound();

        Assert.assertEquals(360, 5000 - c占位符1.getHP());
        Assert.assertEquals(360, 5000 - c占位符2.getHP());
        Assert.assertEquals(360, 5000 - c占位符3.getHP());
        Assert.assertTrue(c占位符1.getStatus().containsStatus(CardStatusType.麻痹));
        Assert.assertFalse(c占位符2.getStatus().containsStatus(CardStatusType.麻痹));
        Assert.assertFalse(c占位符3.getStatus().containsStatus(CardStatusType.麻痹));
    }

    @Test
    public void test雷神降临_杀死() {
        SkillTestContext context = prepare(50, 50, "占位符+雷神降临2", "占位符*3");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1).setBasicHP(2);
        CardInfo c占位符3 = context.addToField(3, 1);
        
        context.startGame();

        random.addNextPicks(0, 1, 2); // 雷神降临
        random.addNextNumbers(0, 0, 0); // 雷神降临

        context.proceedOneRound();

        Assert.assertEquals(165, 5000 - c占位符1.getHP());
        Assert.assertTrue(c占位符2.isDead());
        Assert.assertEquals(165, 5000 - c占位符3.getHP());
        Assert.assertTrue(c占位符1.getStatus().containsStatus(CardStatusType.麻痹));
        Assert.assertTrue(c占位符3.getStatus().containsStatus(CardStatusType.麻痹));
    }

    @Test
    public void test雷暴_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+雷暴5", "占位符*3");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        
        context.startGame();

        random.addNextPicks(0, 1, 2); // 雷暴
        random.addNextNumbers(300, 360, 400); // 雷暴

        context.proceedOneRound();

        Assert.assertEquals(125, 5000 - c占位符1.getHP());
        Assert.assertEquals(125, 5000 - c占位符2.getHP());
        Assert.assertEquals(125, 5000 - c占位符3.getHP());
        Assert.assertTrue(c占位符1.getStatus().containsStatus(CardStatusType.麻痹));
        Assert.assertFalse(c占位符2.getStatus().containsStatus(CardStatusType.麻痹));
        Assert.assertFalse(c占位符3.getStatus().containsStatus(CardStatusType.麻痹));
    }
}
