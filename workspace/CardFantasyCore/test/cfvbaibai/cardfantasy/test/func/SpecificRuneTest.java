package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SpecificRuneTest extends SkillValidationTest {
    /**
     * 玄石: 敌方场上卡多于我方2张时发动, 游戏里描述有误
     */
    @Test
    public void test玄石_激活() {
        SkillTestContext context = prepare(50, 50, "占位符*5", "玄石");
        context.addToField(0, 0);
        RuneInfo r玄石 = context.addToRune(0, 0);
        context.startGame();

        // ROUND 1: 1 - 0
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 2: 1 - 1
        context.addToHand(1, 1).setSummonDelay(0);
        context.proceedOneRound();

        // ROUND 3: 1 - 1
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 4: 1 - 2
        context.addToHand(2, 1).setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 5: 1 - 2
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 6: 1 - 3
        context.addToHand(3, 1).setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());

        // ROUND 7: 1 - 3
        context.proceedOneRound();
        Assert.assertTrue(r玄石.isActivated());

        // ROUND 8: 2 - 3
        context.addToHand(4, 0).setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertTrue(r玄石.isActivated());
        
        // ROUND 9: 2 - 3
        context.proceedOneRound();
        Assert.assertFalse(r玄石.isActivated());
    }

    @Test
    public void test玄石_基本() {
        SkillTestContext context = prepare(50, 50, "占位符*4", "玄石");
        CardInfo placeholder1 = context.addToHand(0, 0).setSummonDelay(6);
        CardInfo placeholder2 = context.addToHand(1, 0).setSummonDelay(6);
        context.addToField(2, 1);
        context.addToField(3, 1);
        RuneInfo r玄石 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(3, placeholder1.getSummonDelay());
        Assert.assertEquals(3, placeholder2.getSummonDelay());
        Assert.assertTrue(r玄石.isActivated());
        Assert.assertEquals(4, r玄石.getEnergy());
    }

    /**
     * 玄石的发动是在召唤之后, 所以虽然本轮可以把某张卡用玄石设到等待0，但还是不能召唤上场
     */
    @Test
    public void test玄石_激活顺序() {
        SkillTestContext context = prepare(50, 50, "占位符*3", "玄石");
        CardInfo placeholder1 = context.addToHand(0, 0).setSummonDelay(2);
        context.addToField(1, 1);
        context.addToField(2, 1);
        RuneInfo r玄石 = context.addToRune(0, 0);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertTrue(r玄石.isActivated());
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertEquals(1, context.getPlayer(0).getHand().size());
        Assert.assertEquals(0, placeholder1.getSummonDelay());
    }
    
    @Test
    public void test龙吟_基本() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "占位符", "秘银巨石像*2", "龙吟");
        Player p0 = context.getPlayer(0);
        context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        RuneInfo r龙吟 = context.addToRune(0, 0);
        context.startGame();

        // ROUND 1
        context.proceedOneRound();
        Assert.assertFalse(r龙吟.isActivated());

        // ROUND 2
        context.proceedOneRound();
        Assert.assertFalse(r龙吟.isActivated());

        // ROUND 3
        context.proceedOneRound();
        Assert.assertFalse(r龙吟.isActivated());
        
        // ROUND 4
        context.proceedOneRound();
        Assert.assertFalse(r龙吟.isActivated());
        int hpNow = p0.getMaxHP() * 60 / 100 - 1;
        p0.setHP(hpNow);

        // ROUND 5
        context.proceedOneRound();
        Assert.assertTrue(r龙吟.isActivated());
        Assert.assertEquals(4, r龙吟.getEnergy());
        Assert.assertEquals(p0.getMaxHP() * 18 / 100, p0.getHP() - hpNow);
    }
    
    @Test
    public void test神祈_激活() {
        SkillTestContext context = prepare(50, 50, "残血森林小兵*2", "占位符", "神祈");
        context.addToField(0, 0);
        context.addToField(2, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        // ROUND 1
        context.proceedOneRound();
        Assert.assertFalse(r神祈.isActivated());
        
        context.proceedOneRound();
        Assert.assertFalse(r神祈.isActivated());
        
        context.addToField(1, 0);
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(4, r神祈.getEnergy());
    }
    
    @Test
    public void test神祈_麻痹() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "独角兽", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c独角兽 = context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextPicks(0).addNextNumbers(0); // 独角兽落雷麻痹见习圣骑成功
        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.麻痹));

        random.addNextNumbers(1000); // 独角兽闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(375, 1055 - c独角兽.getHP());
    }
    
    @Test
    public void test神祈_冰冻() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "蘑菇仙子", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c蘑菇仙子 = context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextPicks(0).addNextNumbers(0); // 蘑菇仙子冰冻见习圣骑成功
        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.冰冻));

        random.addNextNumbers(1000); // 蘑菇仙子闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(375, 550 - c蘑菇仙子.getHP());
    }

    @Test
    public void test神祈_燃烧() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "火象人卫士+闪避", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        random.addNextNumbers(1000); // 火象人卫士闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(100, 590 - c见习圣骑.getHP());

        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();
        
        random.addNextNumbers(1000); // 火象人卫士闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(3, r神祈.getEnergy());
        // 此时燃烧虽然已经被清除，但见习圣骑又打一下，又被烧
        Assert.assertEquals(100 + 100, 590 - c见习圣骑.getHP());

        random.addNextNumbers(0);  // 见习圣骑闪避成功
        context.proceedOneRound();
        Assert.assertEquals(100 + 100, 590 - c见习圣骑.getHP());

        random.addNextNumbers(0); // 火象人卫士闪避成功
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(2, r神祈.getEnergy());
        // 燃烧在回合开始时被清除，本回合见习圣骑的攻击被闪避，所以没有得到新的燃烧状态
        Assert.assertEquals(100 + 100, 590 - c见习圣骑.getHP());
    }
    
    @Test
    public void test神祈_烈火焚神() {
        SkillTestContext context = prepare(50, 50, "占位符", "森林木桩*2", "占位符+烈火焚神1", "神祈");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c森林木桩1 = context.addToField(1, 0);
        CardInfo c森林木桩2 = context.addToField(2, 0);
        context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());

        random.addNextPicks(0, 1, 2); // 烈火焚神烧占位符1
        context.proceedOneRound();
        Assert.assertTrue(c占位符1.getStatus().containsStatus(CardStatusType.燃烧));
        
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(3, r神祈.getEnergy());
        // 烈火焚神的燃烧状态被神祈清除，本方所有卡不损血
        Assert.assertEquals(5000, c占位符1.getHP());
        Assert.assertEquals(5000, c森林木桩1.getHP());
        Assert.assertEquals(5000, c森林木桩2.getHP());
    }
    
    @Test
    public void test神祈_中毒() {
        SkillTestContext context = prepare(50, 50, "占位符", "森林木桩*2", "占位符+毒云1", "神祈");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c森林木桩1 = context.addToField(1, 0);
        CardInfo c森林木桩2 = context.addToField(2, 0);
        context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();
        
        context.proceedOneRound();
        
        random.addNextPicks(0, 1, 2);
        context.proceedOneRound();
        Assert.assertEquals(20 /* 毒云1 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(20 /* 毒云1 */, 5000 - c森林木桩1.getHP());
        Assert.assertEquals(20 /* 毒云1 */, 5000 - c森林木桩2.getHP());
        
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(3, r神祈.getEnergy());
        // 中毒被神祈清除，本方所有卡不进一步损血
        Assert.assertEquals(20 /* 毒云1 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(20 /* 毒云1 */, 5000 - c森林木桩1.getHP());
        Assert.assertEquals(20 /* 毒云1 */, 5000 - c森林木桩2.getHP());
    }

    @Test
    public void test神祈_锁定() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "占位符+陷阱1", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c占位符 = context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextPicks(0).addNextNumbers(0); // 占位符锁定见习圣骑成功
        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.锁定));

        random.addNextNumbers(1000); // 占位符闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(4, r神祈.getEnergy());
        // 锁定状态被神祈解除
        Assert.assertEquals(375, 5000 - c占位符.getHP());
    }

    @Test
    public void test神祈_迷魂() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "占位符+迷魂1", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c占位符 = context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextPicks(0).addNextNumbers(0); // 占位符迷魂见习圣骑成功
        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.迷惑));

        random.addNextNumbers(1000); // 占位符闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(4, r神祈.getEnergy());
        // 迷魂状态被神祈解除
        Assert.assertEquals(375, 5000 - c占位符.getHP());
        Assert.assertEquals(6390, context.getPlayer(0).getHP());
    }

    @Test
    public void test神祈_晕眩() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "占位符+大地之盾", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c占位符 = context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(375, 5000 - c占位符.getHP());
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.晕眩));

        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();

        random.addNextNumbers(1000); // 占位符闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(3, r神祈.getEnergy());
        // 晕眩状态被神祈解除
        Assert.assertEquals(375 + 375, 5000 - c占位符.getHP());
    }

    @Test
    public void test神祈_虚弱() {
        SkillTestContext context = prepare(50, 50, "见习圣骑", "森林木桩*2", "占位符+虚弱", "神祈");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c占位符 = context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(375, 5000 - c占位符.getHP());

        random.addNextPicks(0); // 占位符虚弱见习圣骑
        random.addNextNumbers(0); // 见习圣骑闪避成功
        context.proceedOneRound();
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.弱化));

        random.addNextNumbers(1000); // 占位符闪避失败
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(3, r神祈.getEnergy());
        // 虚弱状态被神祈解除
        Assert.assertEquals(375 + 375, 5000 - c占位符.getHP());
    }

    @Test
    public void test神祈_裂伤() {
        SkillTestContext context = prepare(50, 50, "占位符+回春1", "森林木桩*2", "毁灭之龙", "神祈");
        CardInfo c占位符 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        context.addToField(3, 1);
        RuneInfo r神祈 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();

        random.addNextNumbers(1000); // 毁灭之龙暴击失败
        context.proceedOneRound();
        Assert.assertTrue(c占位符.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(690, 5000 - c占位符.getHP());
        
        context.proceedOneRound();
        Assert.assertTrue(r神祈.isActivated());
        Assert.assertEquals(3, r神祈.getEnergy());
        // 裂伤状态被神祈解除，可以回春
        Assert.assertEquals(690 - 30 /* 回春 */, 5000 - c占位符.getHP());
    }

    @Test
    public void test狂战_基础() {
        SkillTestContext context = prepare(50, 50, "穴居人奴隶-0*2", "狂战", "占位符*2");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符1 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        RuneInfo r狂战 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(r狂战.isActivated());
        Assert.assertEquals(90, 5000 - c占位符1.getHP());
        Assert.assertEquals(90, 5000 - c占位符2.getHP());
        Assert.assertEquals(90 * 150 / 100 * 2 /* 狂战触发的群体穿刺10 */, 6390 - c占位符1.getOwner().getHP());
    }

    @Test
    public void test鹰眼_基础() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像*3", "鹰眼", "占位符+冰甲10*2");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 0).setSummonDelay(0);
        CardInfo c占位符1 = context.addToField(3, 1);
        CardInfo c占位符2 = context.addToField(4, 1);
        RuneInfo r鹰眼 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(r鹰眼.isActivated());
        Assert.assertEquals(660, 5000 - c占位符1.getHP());
        Assert.assertEquals(660, 5000 - c占位符2.getHP());
    }

    @Test
    public void test风暴_基础() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像*2", "风暴", "占位符+免疫", "占位符", "占位符+免疫");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符1 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        CardInfo c占位符3 = context.addToField(4, 1);
        RuneInfo r风暴 = context.addToRune(0, 0);
        context.startGame();

        random.addNextPicks(0, 1, 2); /* 风暴的法力风暴 */
        context.proceedOneRound();
        Assert.assertTrue(r风暴.isActivated());
        Assert.assertEquals(660 + 200 * 3 /* 风暴 */, 5000 - c占位符1.getHP());
        Assert.assertEquals(660 + 200 /* 风暴 */, 5000 - c占位符2.getHP());
        Assert.assertEquals(200 * 3 /* 风暴 */, 5000 - c占位符3.getHP());
    }

    @Test
    public void test磐石_基础() {
        SkillTestContext context = prepare(50, 50, "僵尸犬*2", "磐石", "占位符+送还");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        RuneInfo r磐石 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(r磐石.isActivated());

        context.proceedOneRound();
        Assert.assertTrue(r磐石.isActivated());
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
    }

    /**
     * 磐石不能覆盖手牌区，即对传送无效
     */
    @Test
    public void test磐石_传送() {
        SkillTestContext context = prepare(50, 50, "僵尸犬*3", "磐石", "占位符+传送");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 0);
        context.addToField(3, 1);
        RuneInfo r磐石 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(r磐石.isActivated());

        context.proceedOneRound();
        Assert.assertTrue(r磐石.isActivated());
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(1, context.getPlayer(0).getHand().size());
        Assert.assertEquals(0, context.getPlayer(0).getGrave().size());
    }
}
