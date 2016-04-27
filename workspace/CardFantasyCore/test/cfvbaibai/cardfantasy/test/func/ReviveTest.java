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

    @Test
    public void test夺魂_基本() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+夺魂1", "占位符", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToGrave(2, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();

        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
        Assert.assertEquals(0, context.getPlayer(1).getGrave().size());
        Assert.assertFalse(c占位符2.isDead());
        Assert.assertEquals(context.getPlayer(0), c占位符2.getOwner());
        Assert.assertEquals(context.getPlayer(1), c占位符2.getOriginalOwner());
        Assert.assertEquals(context.getPlayer(1), c占位符1.getOwner());
        Assert.assertEquals(context.getPlayer(1), c占位符1.getOriginalOwner());
    }

    @Test
    public void test夺魂_死亡() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+夺魂1", "占位符", "秘银巨石像", "残血王国小兵");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c秘银巨石像2 = context.addToField(2, 1);
        CardInfo c残血王国小兵 = context.addToGrave(3, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();

        // 刚复活的小兵不能攻击
        Assert.assertEquals(1400, c秘银巨石像2.getHP());
        Assert.assertEquals(context.getPlayer(0), c残血王国小兵.getOwner());

        context.proceedOneRound();
        // 秘银巨石像2杀死叛变的小兵，小兵回到原本主人的墓地
        Assert.assertTrue(context.getPlayer(1).getGrave().contains(c残血王国小兵));
        Assert.assertEquals(1, context.getPlayer(1).getGrave().size());
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(context.getPlayer(1), c残血王国小兵.getOwner());
    }
}
