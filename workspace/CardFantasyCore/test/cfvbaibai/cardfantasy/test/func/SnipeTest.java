package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class SnipeTest extends SkillValidationTest {
    @Test
    public void test狙击_基本() {
        SkillTestContext context = prepare(50, 50, "残血王国小兵", "占位符", "占位符+狙击1");
        CardInfo c王国小兵 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertTrue(c王国小兵.isDead());
    }
}
