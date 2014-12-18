package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class CounterAttackTest extends FeatureValidationTest {
    /**
     * 反击在吸血之后
     */
    @Test
    public void test反击_吸血() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "黑夜蝙蝠-1", "人马巡逻者");
        CardInfo c黑夜蝙蝠 = context.addToField(0, 0).setBasicHP(79);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(21, c黑夜蝙蝠.getHP());
    }
    
    /**
     * 闪避成功的话不触发雷盾
     */
    @Test
    public void test闪避_雷盾() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "秘银巨石像", "大剑圣", "雷盾");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c大剑圣 = context.addToField(1, 1);
        RuneInfo r雷盾 = context.addToRune(0, 1);
        context.startGame();
        
        r雷盾.activate();
        random.addNextNumbers(0); // 大剑圣闪避成功
        context.proceedOneRound();
        
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());
        Assert.assertEquals(0, 995 - c大剑圣.getHP());
    }
    
    /**
     * 伤害为零时不触发雷盾
     */
    @Test
    public void test冰甲岩壁_雷盾() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "光明之龙", "秘银巨石像", "战斗猛犸象", "岩壁", "雷盾");
        CardInfo c光明之龙 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 0);
        CardInfo c战斗猛犸象 = context.addToField(2, 1);
        RuneInfo r岩壁 = context.addToRune(0, 1);
        RuneInfo r雷盾 = context.addToRune(1, 1);
        context.startGame();
        
        r岩壁.activate();
        r雷盾.activate();
        context.proceedOneRound();
        
        Assert.assertEquals(0, 1220 - c战斗猛犸象.getHP());
        Assert.assertEquals(0, 1730 - c光明之龙.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());
    }
    
    /**
     * 横扫伤害到的所有带反伤技能的卡都会触发各自的反伤技能，若是盾刺，则总是以攻击者为中心的三张卡牌受到反伤，雷盾结算方法相同
     */
    @Test
    public void test横扫_雷盾() {
        FeatureTestContext context = FeatureValidationTests.prepare(
            50, 50, "秘银巨石像", "光明之龙", "圣骑士", "凤凰*3", "雷盾");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c光明之龙 = context.addToField(1, 0);
        CardInfo c圣骑士 = context.addToField(2, 0);
        context.addToField(3, 1);
        context.addToField(4, 1);
        context.addToField(5, 1);
        RuneInfo r雷盾 = context.addToRune(0, 1);

        context.startGame();

        r雷盾.activate();
        context.proceedOneRound();

        Assert.assertEquals(800, 1400 - c秘银巨石像.getHP());
        Assert.assertEquals(1000, 1730 - c光明之龙.getHP());
        Assert.assertEquals(800, 1060 - c圣骑士.getHP());
    }
    
    /**
     * 横扫的溅射对象也会发动反伤技能
     */
    @Test
    public void test横扫_反击() {
        FeatureTestContext context = FeatureValidationTests.prepare(50, 50, "光明之龙", "秘银巨石像", "皇家雄狮");
        CardInfo c光明之龙 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();

        Assert.assertEquals(100, 1730 - c光明之龙.getHP());
    }
}
