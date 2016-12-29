package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;


public class MultiAttackTest extends SkillValidationTest {
    /**
     * 连锁攻击和横扫完全分开计算
     */
    @Test
    public void test连锁攻击_横扫() {
        SkillTestContext context = prepare(50, 50, "战场女武神", "占位符*4");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        CardInfo c占位符4 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(805, 5000 - c占位符1.getHP());
        Assert.assertEquals(805 + 805 * 175 / 100, 5000 - c占位符2.getHP());
        Assert.assertEquals(805 * 175 / 100, 5000 - c占位符3.getHP());
        Assert.assertEquals(805 * 175 / 100, 5000 - c占位符4.getHP());
    }

    /**
     * 连锁攻击能触发弱点攻击破冰甲
     */
    @Test
    public void test连锁攻击_弱点攻击_冰甲() {
        SkillTestContext context = prepare(50, 50, "纯洁圣女+弱点攻击", "占位符+冰甲10*2");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(750 + 240 /* 背刺6 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(750 + 240 /* 背刺6 *//* 连锁攻击4 */, 5000 - c占位符2.getHP());
    }

    /**
     * 横扫的溅射无法触发毒刃
     */
    @Test
    public void test横扫_毒刃() {
        SkillTestContext context = prepare(50, 50, "生化异形", "占位符*2");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(665, 5000 - c占位符1.getHP());
        Assert.assertEquals(665, 5000 - c占位符2.getHP());
        Assert.assertEquals(665, 5000 - c占位符1.getMaxHP());
        Assert.assertEquals(0, 5000 - c占位符2.getMaxHP());
    }

    @Test
    public void test修罗地火攻_普通() {
        SkillTestContext context = prepare(50, 50, "魔装机神-0", "占位符*3");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(320 /* 普通攻击 */ + (180 + 90 * 3) /* 修罗地火攻 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(180 + 90 * 3, 5000 - c占位符2.getHP());
        Assert.assertEquals(180 + 90 * 3, 5000 - c占位符3.getHP());
    }
    
    @Test
    public void test修罗地火攻_免疫() {
        SkillTestContext context = prepare(50, 50, "魔装机神-0", "占位符+免疫*2", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(320 /* 普通攻击 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(0, 5000 - c占位符2.getHP());
        Assert.assertEquals(180 + 90 * 3, 5000 - c占位符3.getHP());
    }

    @Test
    public void test修罗地火攻_法力反射() {
        SkillTestContext context = prepare(50, 50, "魔装机神-0", "占位符+法力反射1*2", "占位符");
        CardInfo c魔装机神 = context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(30 * 2 /* 法力反射 */, 1830 - c魔装机神.getHP()); 
        Assert.assertEquals(320 /* 普通攻击 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(0, 5000 - c占位符2.getHP());
        Assert.assertEquals(180 + 90 * 3, 5000 - c占位符3.getHP());
    }
    
    @Test
    public void test生命链接_普通() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "秘银巨石像", "占位符", "占位符+生命链接", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        CardInfo c占位符4 = context.addToField(4, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(220, 5000 - c占位符2.getHP());
        Assert.assertEquals(220, 5000 - c占位符3.getHP());
        Assert.assertEquals(220, 5000 - c占位符4.getHP());
    }
    
    @Test
    public void test生命链接_两卡分担() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像*2", "占位符+生命链接", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c占位符1 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(330, 5000 - c占位符1.getHP());
        Assert.assertEquals(990, 5000 - c占位符2.getHP());
    }
    
    @Test
    public void test生命链接_无卡分担() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "占位符+生命链接");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(660, 5000 - c占位符.getHP());
    }
    
    @Test
    public void test生命链接_吸血() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "秘银巨石像+吸血10", "占位符", "占位符+生命链接", "占位符");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 0).setBasicHP(1);
        CardInfo c占位符2 = context.addToField(2, 1);
        CardInfo c占位符3 = context.addToField(3, 1);
        CardInfo c占位符4 = context.addToField(4, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(270, 5000 - c占位符2.getHP());
        Assert.assertEquals(270, 5000 - c占位符3.getHP());
        Assert.assertEquals(270, 5000 - c占位符4.getHP());
        Assert.assertEquals(811, c秘银巨石像.getHP());
    }

    @Test
    public void test连击_普通() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+连击", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(810 * 2, 5000 - c占位符.getHP());
    }

    @Test
    public void test连击_无对应卡牌() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+连击");
        context.addToField(0, 0);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(810, context.getPlayer(1).getMaxHP() - context.getPlayer(1).getHP());
    }

    /**
     * 连击（无不动）遇到反射装甲时，连续击打两次后才会被送还
     */
    @Test
    public void test连击_反射装甲() {
        SkillTestContext context = prepare(50, 50, "魔剑士+连击", "占位符+反射装甲");
        CardInfo c魔剑士 = context.addToField(0, 0);
        @SuppressWarnings("unused")
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(0, c魔剑士.getOwner().getField().size());
        Assert.assertEquals(1, c魔剑士.getOwner().getDeck().size());
        // TODO: Disable this assertion temporarily 
        //Assert.assertEquals(275 * 2, 5000 - c占位符.getHP());
    }

    @Test
    public void test连击_第一击杀死() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+连击", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
        Assert.assertEquals(0, context.getPlayer(1).getMaxHP() - context.getPlayer(1).getHP());
    }

    @Test
    public void test连击_精准打击() {
        SkillTestContext context = prepare(50, 50, "机械拳皇", "占位符");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals((int)(880 * 1.8 * 2), context.getPlayer(1).getMaxHP() - context.getPlayer(1).getHP());
        Assert.assertEquals(880 * 2, 5000 - c占位符.getHP());
    }
}
