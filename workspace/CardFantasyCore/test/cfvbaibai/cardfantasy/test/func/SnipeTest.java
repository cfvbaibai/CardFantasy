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
    
    @Test
    public void test狙击_神箭三重奏() {
        SkillTestContext context = prepare(50, 50, "残血王国小兵","残血王国小兵","残血王国小兵","占位符","占位符","占位符", "占位符+神箭三重奏1");
        CardInfo c王国小兵1 = context.addToField(0, 0);
        CardInfo c王国小兵2 =context.addToField(1, 0);
        CardInfo c王国小兵3 =context.addToField(2, 0);
        context.addToField(3, 1);
        context.addToField(4, 1);
        context.addToField(5, 1);
        context.addToField(6, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertTrue(c王国小兵1.isDead()&&c王国小兵2.isDead()&&c王国小兵3.isDead());
    }
    
    @Test
    public void test精准打击_基本() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+精准打击7", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符.getHP());
        Assert.assertEquals(810 * 170 / 100, 6390 - c占位符.getOwner().getHP());
    }

    @Test
    public void test武形秘箭_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+武形秘箭5", "占位符", "占位符+免疫", "占位符+法力反射10", "占位符+冰甲10");
        context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 1);
        CardInfo c占位符3 = context.addToField(2, 1);
        CardInfo c占位符4 = context.addToField(3, 1);
        CardInfo c占位符5 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(350, 5000 - c占位符2.getHP());
        Assert.assertEquals(350, 5000 - c占位符3.getHP());
        Assert.assertEquals(350, 5000 - c占位符4.getHP());
        Assert.assertEquals(350, 5000 - c占位符5.getHP());
    }

    @Test
    public void test武形神箭_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+武形神箭5", "占位符", "占位符+免疫", "占位符+法力反射10", "占位符+冰甲10");
        context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 1).setBasicHP(4000);
        CardInfo c占位符3 = context.addToField(2, 1).setBasicHP(4000);
        CardInfo c占位符4 = context.addToField(3, 1);
        CardInfo c占位符5 = context.addToField(4, 1).setBasicHP(4000);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(350 * 2, 4000 - c占位符2.getHP());
        Assert.assertEquals(350 * 2, 4000 - c占位符3.getHP());
        Assert.assertEquals(350, 5000 - c占位符4.getHP());
        Assert.assertEquals(350 * 2, 4000 - c占位符5.getHP());
    }

    @Test
    public void test魔神之刃_基本() {
        SkillTestContext context = prepare(50, 50, "占位符+魔神之刃3", "占位符", "占位符", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1).setBasicHP(4000);
        CardInfo c占位符3 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(5000, c占位符1.getHP());
        Assert.assertEquals(1000, c占位符2.getHP());
        Assert.assertEquals(5000, c占位符3.getHP());
    }
}
