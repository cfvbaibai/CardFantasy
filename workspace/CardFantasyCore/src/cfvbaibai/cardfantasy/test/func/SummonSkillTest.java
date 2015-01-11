package cfvbaibai.cardfantasy.test.func;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SummonSkillTest extends SkillValidationTest {
    /**
     * 降临技能在符文发动和结算之前发动
     */
    @Test
    public void test降临摧毁_焚天() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "独眼巨人", "焚天", "凤凰-5*2");
        context.addToHand(0, 0).setSummonDelay(0);
        RuneInfo r焚天 = context.addToRune(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 独眼巨人摧毁
        context.proceedOneRound();

        // 降临技能先结算，杀死一只凤凰，对方场上只剩一张森林卡，无法激活焚天
        Assert.assertFalse("焚天应该未激活", r焚天.isActivated());
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
    }
    
    /**
     * 有多张卡牌等待时间相同的情况下，降临传送会杀死其中最靠前的那张
     */
    @Test
    public void test降临传送_相同等待时间() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "隐世先知", "金属巨龙-5", "凤凰-5");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c金属巨龙 = context.addToHand(1, 1).setSummonDelay(3);
        CardInfo c凤凰 = context.addToHand(2, 1).setSummonDelay(3);
        context.startGame();

        context.proceedOneRound();

        List<CardInfo> handListB = context.getPlayer(1).getHand().toList();
        Assert.assertTrue("凤凰应该还活着", handListB.contains(c凤凰));
        Assert.assertFalse("金属巨龙应该死了", handListB.contains(c金属巨龙));
    }

    /**
     * 献祭能被免疫
     */
    @Test
    public void test献祭_免疫() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符+献祭", "占位符+免疫");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        // 被免疫
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
    }
    
    
    /**
     * 献祭和时光倒流是同一个优先级的技能，谁在先就谁先发动
     */
    @Test
    public void test献祭_时光倒流() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符+献祭", "占位符+时光倒流");
        CardInfo c占位符1 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(c占位符1, context.getPlayer(0).getField().getCard(0));
    }

    /**
     * 献祭和时光倒流是同一个优先级的技能，谁在先就谁先发动
     */
    @Test
    public void test时光倒流_献祭() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符+时光倒流", "占位符+献祭");
        CardInfo c占位符1 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(0); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(c占位符1, context.getPlayer(0).getField().getCard(0));
    }

    /**
     * 种族之力比献祭优先级高，而且献祭的攻击力增幅包含种族之力
     */
    @Test
    public void test种族之力_献祭() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像+献祭8", "占位符", "隐世先知");
        CardInfo c秘银巨石像 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c隐世先知 = context.addToHand(2, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getField().getCard(0).getUniqueName());
        Assert.assertEquals(1970, c秘银巨石像.getCurrentAT());
        Assert.assertEquals(c隐世先知.getUniqueName(), context.getPlayer(0).getField().getCard(1).getUniqueName());
    }


    /**
     * 种族之力比献祭优先级高，而且献祭的攻击力增幅计算包含种族之力,
     * 计算方法为：(基础+BUFF)*献祭系数-BUFF
     */
    @Test
    public void test种族之力_献祭_离场() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像+献祭8", "隐世先知");
        CardInfo c秘银巨石像 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getField().getCard(0).getUniqueName());
        Assert.assertEquals(1795, c秘银巨石像.getCurrentAT());
    }

    /**
     * 被动种族守护发动在前，降临技能发动在后，保证不被法力反射弹死 
     */
    @Test
    public void test被动种族守护_降临暴风雪_法力反射_分别上场() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "圣骑士", "残血王国小兵+降临暴风雪1", "元素灵龙");
        context.addToField(0, 0);
        CardInfo c王国小兵 = context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 降临暴风雪
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(c王国小兵.getUniqueName(), context.getPlayer(0).getField().getCard(1).getUniqueName());
        Assert.assertEquals(81, c王国小兵.getHP());
    }

    /**
     * 降临技能在种族守护之后触发
     */
    @Test
    public void test被动种族守护_降临暴风雪_法力反射_同时上场() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50,
            "残血王国小兵+降临暴风雪1", "占位符+王国守护10", "元素灵龙");
        CardInfo c残血王国小兵 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        context.startGame();
        
        random.addNextPicks(0).addNextNumbers(1000); // 降临暴风雪
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(1 + 500 /* 王国守护10 */ - 120 /* 法力反射 */, c残血王国小兵.getHP());
    }

    /**
     * 种族守护比献祭优先级高，而且献祭的体力增幅计算不包含种族守护,
     * 计算方法为：基础*献祭系数
     */
    @Test
    public void test种族守护_献祭_离场() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "邪灵女巫-0", "堕落精灵+地狱守护5");
        CardInfo c邪灵女巫 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(c邪灵女巫.getUniqueName(), context.getPlayer(0).getField().getCard(0).getUniqueName());
        Assert.assertEquals(1050, c邪灵女巫.getMaxHP());
    }
    
    /**
     * 降临技能优先于献祭
     */
    @Test
    public void test降临烈焰风暴_献祭() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像+献祭8", "占位符+降临烈焰风暴1", "占位符");
        CardInfo c秘银巨石像 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 降临烈焰风暴
        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getField().getCard(0).getUniqueName());
        Assert.assertEquals(810 * 2 /* 秘银普攻 */ + 25 /* 降临烈焰风暴 */, 5000 - c占位符2.getHP());
    }

    /**
     * 即使在受控的状况下，依然可以使用神性祈求
     */
    @Test
    public void test神性祈求_封印() {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "占位符", "占位符+封印", "秘银巨石像+神性祈求", "秘银巨石像");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c占位符2 = context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        // 两个秘银都被封印
        context.proceedOneRound();

        context.proceedOneRound();
        // 两个秘银的封印都被神性祈求解除
        Assert.assertEquals(810, 5000 - c占位符1.getHP());
        Assert.assertEquals(660, 5000 - c占位符2.getHP());
    }
}
