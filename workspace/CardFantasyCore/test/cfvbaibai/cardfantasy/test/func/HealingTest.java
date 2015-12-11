package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;

public class HealingTest extends SkillValidationTest {
    @Test
    public void test治疗之雾_三卡治疗() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像*4", "东方幻术师");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c东方幻术师 = context.addToField(4, 0).setBasicHP(100);
        CardInfo c秘银巨石像3 = context.addToField(2, 0).setBasicHP(100);
        CardInfo c秘银巨石像4 = context.addToField(3, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(100, c秘银巨石像1.getHP());
        Assert.assertEquals(300, c秘银巨石像2.getHP());
        Assert.assertEquals(300, c东方幻术师.getHP());
        Assert.assertEquals(300, c秘银巨石像3.getHP());
        Assert.assertEquals(100, c秘银巨石像4.getHP());
    }

    @Test
    public void test治疗之雾_两卡治疗_左面() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像*2", "东方幻术师");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c东方幻术师 = context.addToField(2, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(100, c秘银巨石像1.getHP());
        Assert.assertEquals(300, c秘银巨石像2.getHP());
        Assert.assertEquals(300, c东方幻术师.getHP());
    }

    @Test
    public void test治疗之雾_两卡治疗_右面() {
        SkillTestContext context = prepare(
            50, 50, "东方幻术师", "秘银巨石像*2");
        CardInfo c东方幻术师 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c秘银巨石像1 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(2, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(300, c东方幻术师.getHP());
        Assert.assertEquals(300, c秘银巨石像1.getHP());
        Assert.assertEquals(100, c秘银巨石像2.getHP());
    }

    @Test
    public void test治疗之雾_单卡治疗() {
        SkillTestContext context = prepare(
            50, 50, "东方幻术师");
        CardInfo c东方幻术师 = context.addToField(0, 0).setBasicHP(100);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(300, c东方幻术师.getHP());
    }

    @Test
    public void test治疗之雾_部分治疗() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "东方幻术师", "秘银巨石像");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(1400);
        CardInfo c东方幻术师 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c秘银巨石像2 = context.addToField(2, 0).setBasicHP(1300);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(1400, c秘银巨石像1.getHP());
        Assert.assertEquals(300, c东方幻术师.getHP());
        Assert.assertEquals(1400, c秘银巨石像2.getHP());
    }

    @Test
    public void test月神的护佑_普通() {
        SkillTestContext context = prepare(
            50, 50, "占位符*3", "占位符+月神的护佑5");
        CardInfo c占位符1 = context.addToField(0, 0).setBasicHP(100);
        CardInfo c占位符2 = context.addToField(1, 0).setBasicHP(1300);
        CardInfo c占位符3 = context.addToField(2, 0).setBasicHP(4500);
        CardInfo c占位符4 = context.addToField(3, 0);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(1100, c占位符1.getHP());
        Assert.assertEquals(2300, c占位符2.getHP());
        Assert.assertEquals(5000, c占位符3.getHP());
        Assert.assertEquals(5000, c占位符4.getHP());
    }

    @Test
    public void test月神的护佑_不同等级() {
        SkillTestContext context = prepare(
            50, 50, "占位符*3", "占位符+月神的护佑9");
        CardInfo c占位符1 = context.addToField(0, 0).setBasicHP(1300);
        CardInfo c占位符2 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c占位符3 = context.addToField(2, 0).setBasicHP(4500);
        CardInfo c占位符4 = context.addToField(3, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(2800, c占位符1.getHP());
        Assert.assertEquals(1600, c占位符2.getHP());
        Assert.assertEquals(5000, c占位符3.getHP());
        Assert.assertEquals(5000, c占位符4.getHP());
    }

    @Test
    public void test月神的触碰_普通() {
        SkillTestContext context = prepare(
            50, 50, "占位符*3", "占位符+月神的触碰5");
        CardInfo c占位符1 = context.addToField(0, 0).setBasicHP(1300);
        CardInfo c占位符2 = context.addToField(1, 0).setBasicHP(100);
        CardInfo c占位符3 = context.addToField(2, 0).setBasicHP(4500);
        CardInfo c占位符4 = context.addToField(3, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1300, c占位符1.getHP());
        Assert.assertEquals(1100, c占位符2.getHP());
        Assert.assertEquals(4500, c占位符3.getHP());
        Assert.assertEquals(5000, c占位符4.getHP());
    }

    @Test
    public void test祈祷_普通() {
        SkillTestContext context = prepare(50, 50, "占位符+祈祷10", "占位符");
        context.addToField(0, 0);
        Player player1 = context.getStage().getPlayers().get(0);
        player1.setHP(100);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(100 + 500, player1.getHP());
    }
    
    @Test
    public void test祈祷_死亡() {
        SkillTestContext context = prepare(50, 50, "占位符+魔神之咒10", "占位符+祈祷10");
        context.addToField(0, 0);
        context.addToField(1, 1);
        Player player2 = context.getStage().getPlayers().get(1);
        player2.setHP(100);
        context.startGame();

        GameResult result1 = context.proceedOneRound();
        Assert.assertEquals(GameEndCause.一时中断, result1.getCause());
        GameResult result2 = context.proceedOneRound();
        Assert.assertEquals(GameEndCause.英雄死亡, result2.getCause());
        Assert.assertEquals("PlayerA", result2.getWinner().getId());
    }

    @Test
    public void test祈祷_自动扣血() {
        SkillTestContext context = prepare(50, 50, "占位符+祈祷10", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 1);
        Player player1 = context.getStage().getPlayers().get(0);
        player1.setHP(250);
        context.getStage().setRound(120);
        context.startGame();

        GameResult result = context.proceedOneRound();
        Assert.assertEquals(GameEndCause.英雄死亡, result.getCause());
        Assert.assertEquals("PlayerB", result.getWinner().getId());
    }
}
