package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;

public class DeathSkillTest extends SkillValidationTest {
    
    /**
     * 法力反射无法防御自爆
     */
    @Test
    public void test法力反射_自爆() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "元素灵龙", "哥布林术士");
        CardInfo c元素灵龙 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 哥布林术士自爆
        context.proceedOneRound();

        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(120, 1480 - c元素灵龙.getHP());
    }
    

    @Test
    public void test死亡印记_基本() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "占位符", "秘银巨石像+死亡印记1", "占位符", "残血王国小兵", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c王国小兵 = context.addToField(3, 1);
        CardInfo c占位符3 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(1).getField().size());
        Assert.assertEquals(50, 5000 - c占位符2.getHP());
        Assert.assertEquals(50, 5000 - c占位符3.getHP());
        Assert.assertTrue(c王国小兵.isDead());
    }

    /**
     * 印记过了一回合会消失
     */
    @Test
    public void test死亡印记_一回合消失() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "残血王国小兵", "秘银巨石像+死亡印记1", "秘银巨石像", "占位符", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像2 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        CardInfo c占位符3 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符2.getStatus().containsStatus(CardStatusType.死印));

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());

        c秘银巨石像2.setBasicHP(2);
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(1).getField().size());
        Assert.assertTrue(c秘银巨石像2.isDead());
        Assert.assertEquals(810 /* 首回合普通攻击 */ + 50 /* 死亡印记 */, 5000 - c占位符2.getHP());
        Assert.assertEquals(0, 5000 - c占位符3.getHP());
    }

    /**
     * 死亡印记能触发死契技能
     */
    @Test
    public void test死亡印记_死契技能() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "秘银巨石像+死亡印记1", "残血王国小兵+死契群体削弱10*2");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(0, 1550 - c秘银巨石像.getHP());
        Assert.assertEquals(100 /* 两次群体削弱10 */, 810 - c秘银巨石像.getCurrentAT());
    }

    @Test
    public void test死亡印记_对方回合() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "占位符+死亡印记1", "森林木桩*2", "秘银巨石像", "占位符", "雷盾");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c秘银巨石像 = context.addToField(3, 1);
        CardInfo c占位符2 = context.addToField(4, 1);
        context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();

        c秘银巨石像.setBasicHP(2);
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
        Assert.assertTrue(c秘银巨石像.isDead());
        Assert.assertEquals(200 /* 此时死亡印记已过，无法发生爆炸，只有盾刺伤害 */, 5000 - c占位符2.getHP());
    }

    @Test
    public void test死亡印记_对方回合_前方队友弹死自己() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "森林木桩", "占位符+死亡印记1", "森林木桩", "秘银巨石像", "残血王国小兵", "占位符", "雷盾");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c秘银巨石像 = context.addToField(3, 1);
        CardInfo c王国小兵 = context.addToField(4, 1);
        CardInfo c占位符2 = context.addToField(5, 1);
        context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(1).getField().size());
        Assert.assertTrue(c王国小兵.isDead());
        Assert.assertEquals(200 /* 雷盾 */ + 50 /* 死亡印记 */, 1400 - c秘银巨石像.getHP());
        Assert.assertEquals(50 /* 死亡印记 */, 5000 - c占位符2.getHP());
    }

    @Test
    public void test死亡印记_免疫爆炸() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "秘银巨石像+死亡印记1", "残血王国小兵", "占位符+免疫");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
        Assert.assertEquals(0 /* 死亡印记的爆炸被免疫 */, 5000 - c占位符.getHP());
    }

    @Test
    public void test死亡印记_免疫被印() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "秘银巨石像+死亡印记1", "残血王国小兵+免疫", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
        Assert.assertEquals(0 /* 死亡印记被免疫无法造成爆炸 */, 5000 - c占位符.getHP());
    }

    /**
     * 当触发不屈时，死亡印记不会爆炸
     */
    @Test
    public void test死亡印记_不屈_不爆炸() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "秘银巨石像+死亡印记1", "残血王国小兵+不屈", "占位符");
        context.addToField(0, 0);
        CardInfo c王国小兵 = context.addToField(1, 1);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertTrue(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(2, context.getPlayer(1).getField().size());
        // 由于不屈，死亡印记并没有爆炸
        Assert.assertEquals(0, 5000 - c占位符.getHP());
        
        context.proceedOneRound();
        
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
        Assert.assertEquals(50, 5000 - c占位符.getHP());
    }
}
