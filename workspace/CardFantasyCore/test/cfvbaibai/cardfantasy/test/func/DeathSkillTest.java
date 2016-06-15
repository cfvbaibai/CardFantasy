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
        SkillTestContext context = prepare(50, 50, "元素灵龙", "哥布林术士");
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
        SkillTestContext context = prepare(
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
    
    @Test
    public void test燕返_普通() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+冰弹10", "月镰杀手");
        CardInfo c占位符1 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 1).setBasicHP(2);
        context.startGame();

        random.addNextNumbers(0); // 月镰杀手闪避
        random.addNextPicks(0).addNextNumbers(1000); // 冰弹10
        context.proceedOneRound();
        
        Assert.assertEquals(565 * 2, 5000 - c占位符1.getHP());
    }
    
    /**
     * 不屈可以造成燕返触发两次
     * 免疫无法阻挡燕返
     * 格挡无法削弱燕返伤害
     */
    @Test
    public void test燕返_不屈_免疫_格挡() {
        SkillTestContext context = prepare(50, 50, "金属巨龙-15*2", "占位符", "欲望惩罚者");
        CardInfo c金属巨龙1 = context.addToField(0, 0);
        CardInfo c金属巨龙2 = context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c欲望惩罚者 = context.addToField(3, 1).setBasicHP(2);
        context.startGame();
        
        random.addNextNumbers(0);    // 金属巨龙1暴击
        context.proceedOneRound();
        Assert.assertEquals(815 * 2, 1825 - c金属巨龙1.getHP());
        Assert.assertTrue(c欲望惩罚者.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(!c欲望惩罚者.isDead());
        
        context.proceedOneRound();
        Assert.assertTrue(c金属巨龙1.isDead());

        random.addNextNumbers(0);   // 金属巨龙2暴击
        context.proceedOneRound();
        Assert.assertEquals(815 * 2, 1825 - c金属巨龙2.getHP());
        Assert.assertTrue(c欲望惩罚者.isDead());
    }

    @Test
    public void test燕返_摧毁() {
        SkillTestContext context = prepare(50, 50, "占位符", "独眼巨人", "欲望惩罚者");
        CardInfo c占位符 = context.addToField(0, 0);
        CardInfo c独眼巨人 = context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c欲望惩罚者 = context.addToField(2, 1);
        context.startGame();
        
        random.addNextPicks(0); // 摧毁
        context.proceedOneRound();
        Assert.assertTrue(c欲望惩罚者.isDead());
        Assert.assertEquals(815 * 2, 5000 - c占位符.getHP());
        Assert.assertEquals(0, 1180 - c独眼巨人.getHP());
    }

    @Test
    public void test扼杀_普通() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+扼杀", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
        Assert.assertEquals(0, c占位符.getOwner().getGrave().size());
        Assert.assertEquals(1, c占位符.getOwner().getOutField().size());
        Assert.assertEquals(0, c占位符.getOwner().getHand().size());
    }

    @Test
    public void test扼杀_转生() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+扼杀", "占位符+转生");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextNumbers(0);     // 保证转生成功
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
        Assert.assertEquals(0, c占位符.getOwner().getGrave().size());
        Assert.assertEquals(1, c占位符.getOwner().getOutField().size());
        Assert.assertEquals(0, c占位符.getOwner().getHand().size());
    }

    @Test
    public void test扼杀_免疫_转生() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+扼杀", "金属巨龙+转生");
        context.addToField(0, 0);
        CardInfo c金属巨龙 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextNumbers(0);     // 保证转生成功
        context.proceedOneRound();
        Assert.assertTrue(c金属巨龙.isDead());
        Assert.assertEquals(0, c金属巨龙.getOwner().getGrave().size());
        Assert.assertEquals(1, c金属巨龙.getOwner().getOutField().size());
        Assert.assertEquals(0, c金属巨龙.getOwner().getHand().size());
    }

    @Test
    public void test武形天火击_普通() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    @Test
    public void test武形天火击_免疫() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者", "占位符+免疫");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertFalse(c占位符.isDead());
        Assert.assertEquals(790, 5000 - c占位符.getHP());
    }

    @Test
    public void test武形天火击_免疫_沉默() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者+降临沉默", "占位符+免疫");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    @Test
    public void test武形天火击_不动() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者", "占位符+不动");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertFalse(c占位符.isDead());
        Assert.assertEquals(790, 5000 - c占位符.getHP());
        Assert.assertTrue(c占位符.getStatus().containsStatus(CardStatusType.燃烧));
    }

    @Test
    public void test武形天火击_不动_沉默() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者+沉默", "占位符+不动");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    @Test
    public void test武形天火击_闪避() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者", "占位符+闪避10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        random.addNextNumbers(0);
        context.proceedOneRound();
        Assert.assertFalse(c占位符.isDead());
        Assert.assertEquals(0, 5000 - c占位符.getHP());
        Assert.assertTrue(c占位符.getStatus().containsStatus(CardStatusType.燃烧));
    }

    @Test
    public void test武形天火击_物理攻击已致死() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    @Test
    public void test武形天火击_不屈() {
        SkillTestContext context = prepare(50, 50, "武形火焰尊者", "占位符+不屈");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }

    @Test
    public void test武形天火击_燕返_不屈() {
        SkillTestContext context = prepare(50, 50, "元素灵龙+武形天火击", "欲望惩罚者");
        CardInfo c元素灵龙 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c欲望惩罚者 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c元素灵龙.isDead());
        Assert.assertTrue(c欲望惩罚者.isDead());
    }

    @Test
    public void test武形天火击_盾刺_死亡() {
        SkillTestContext context = prepare(50, 50, "元素灵龙+武形天火击", "占位符+盾刺10");
        CardInfo c元素灵龙 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertTrue(c元素灵龙.isDead());
        Assert.assertTrue(c占位符.isDead());
    }
}
