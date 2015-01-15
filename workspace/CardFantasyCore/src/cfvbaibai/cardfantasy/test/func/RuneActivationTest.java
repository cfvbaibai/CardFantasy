package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class RuneActivationTest extends SkillValidationTest {

    /**
     * 石林激活后，如果被回魂拉走墓地蛮荒卡导致蛮荒不够2张，则会失去激活状态
     */
    @Test
    public void test回魂_石林() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "冥河船夫", "战斗猛犸象*2", "石林", "凤凰");
        CardInfo c冥河船夫 = context.addToField(0, 0);
        context.addToGrave(1, 0);
        context.addToGrave(2, 0);
        RuneInfo r石林 = context.addToRune(0, 0);
        CardInfo c凤凰 = context.addToField(3, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 冥河船夫回魂战斗猛犸象1
        context.proceedOneRound();
        Assert.assertTrue("石林应该已激活", r石林.isActivated());

        random.addNextPicks(0).addNextNumbers(0); // 凤凰烈焰风暴攻击冥河船夫
        context.proceedOneRound();
        Assert.assertEquals(660 /* 凤凰攻击 */, 1180 - c冥河船夫.getHP()); // 烈焰风暴被石林抵挡 
        Assert.assertEquals(500 * 205 / 100 /* 冥河船夫污染 */ + 270 /* 石林 */ - 210 /* 回春 */, 1560 - c凤凰.getHP());

        // 强行恢复HP，不然HP不够测了
        c凤凰.setBasicHP(1560); 
        c冥河船夫.setBasicHP(1180);

        random.addNextPicks(0).addNextNumbers(0); // 冥河船夫回魂战斗猛犸象2
        context.proceedOneRound();
        Assert.assertFalse("石林应该未激活", r石林.isActivated());

        random.addNextPicks(0).addNextNumbers(0); // 凤凰烈焰风暴攻击冥河船夫
        context.proceedOneRound();
        Assert.assertEquals(660 /* 凤凰攻击 */ + 200 /* 烈焰风暴 */, 1180 - c冥河船夫.getHP());
        Assert.assertEquals(500 * 205 / 100 /* 冥河船夫污染 */ - 210 /* 回春 */, 1560 - c凤凰.getHP());

        Assert.assertEquals(1, r石林.getMaxEnergy() - r石林.getEnergy());
    }
    
    /**
     * 场上即使有2张王国卡，但若没有卡受伤，清泉不激活
     */
    @Test
    public void test清泉_激活条件() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "秘银巨石像*2", "清泉", "秘银巨石像");
        CardInfo c秘银巨石像1 = context.addToField(0, 0);
        context.addToField(1, 0);
        RuneInfo r清泉 = context.addToRune(0, 0);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertFalse("清泉应该未激活", r清泉.isActivated());

        context.proceedOneRound();
        Assert.assertEquals(660, 1400 - c秘银巨石像1.getHP());

        context.proceedOneRound();
        Assert.assertTrue("清泉应该已激活", r清泉.isActivated());
        Assert.assertEquals(660 - 225 /* 清泉 */, 1400 - c秘银巨石像1.getHP());

        Assert.assertEquals(1, r清泉.getMaxEnergy() - r清泉.getEnergy());
    }
    
    /**
     * 对方场上没有卡时也会激活雷盾、岩壁和赤谷
     */
    @Test
    public void test雷盾岩壁赤谷_激活条件() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "战斗猛犸象*2", "金属巨龙*2", "雷盾", "岩壁", "赤谷");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        context.addToField(3, 0);
        RuneInfo r雷盾 = context.addToRune(0, 0);
        RuneInfo r岩壁 = context.addToRune(1, 0);
        RuneInfo r赤谷 = context.addToRune(2, 0);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertTrue("雷盾应该已激活", r雷盾.isActivated());
        Assert.assertEquals(1, r雷盾.getMaxEnergy() - r雷盾.getEnergy());
        Assert.assertTrue("岩壁应该已激活", r岩壁.isActivated());
        Assert.assertEquals(1, r岩壁.getMaxEnergy() - r岩壁.getEnergy());
        Assert.assertTrue("赤谷应该已激活", r赤谷.isActivated());
        Assert.assertEquals(1, r赤谷.getMaxEnergy() - r赤谷.getEnergy());
    }
}
