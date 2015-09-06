package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.RealRandomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class ReviveTest extends SkillValidationTest {
    @Test
    public void test回魂_随机性() {
        SkillTestContext context = prepare(
            50, 50, "森林女神", "秘银巨石像", "圣骑士", "魔剑士", "占位符");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToGrave(1, 0);
        CardInfo c圣骑士 = context.addToGrave(2, 0);
        context.addToGrave(3, 0);
        context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1, 2).addNextNumbers(0, 0);
        context.proceedOneRound();

        Assert.assertEquals(c圣骑士.getUniqueName(), context.getPlayer(0).getDeck().toList().get(1).getUniqueName());
        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getDeck().toList().get(0).getUniqueName());
        Assert.assertEquals(2, context.getPlayer(0).getDeck().size());
    }
    
    /**
     * 死契回魂不能回魂自己
     */
    @Test
    public void test死契回魂() {
        SkillTestContext context = prepare(
            50, 50, "战斗猛犸象+死契回魂1", "秘银巨石像", "圣骑士", "占位符+盾刺10");
        context.addToField(0, 0).setBasicHP(2);
        CardInfo c秘银巨石像 = context.addToGrave(1, 0);
        context.addToGrave(2, 0);
        context.addToField(3, 1);
        context.startGame();

        random.addNextPicks(2).addNextNumbers(0);
        context.proceedOneRound();

        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getDeck().toList().get(0).getUniqueName());
        Assert.assertEquals(1, context.getPlayer(0).getDeck().size());
        Assert.assertEquals(2, context.getPlayer(0).getGrave().size());
    }
    
    @Test
    public void test死契回魂_真随机() {
        Randomizer original = Randomizer.getRandomizer();
        Randomizer.registerRandomizer(new RealRandomizer());
        try {
            SkillTestContext context = prepare(
                50, 50, "战斗猛犸象+死契回魂2", "占位符*3", "占位符+盾刺10");
            Randomizer.getRandomizer().setUI(context.getStage().getUI());
            context.addToField(0, 0).setBasicHP(2);
            context.addToGrave(1, 0);
            context.addToGrave(2, 0);
            context.addToGrave(3, 0);
            context.addToField(4, 1);
            context.startGame();
            context.proceedOneRound();
            Assert.assertEquals(2, context.getPlayer(0).getGrave().size());
            Assert.assertEquals(2, context.getPlayer(0).getDeck().size());
        } finally {
            Randomizer.registerRandomizer(original);
        }
    }
}
