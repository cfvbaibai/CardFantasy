package cfvbaibai.cardfantasy.test.func;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SummonSkillTest extends SkillValidationTest {
    /**
     * 降临技能在符文发动和结算之前发动
     */
    @Test
    public void test降临摧毁_焚天() {
        SkillTestContext context = prepare(50, 50, "独眼巨人", "焚天", "凤凰-5*2");
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
        SkillTestContext context = prepare(50, 50, "隐世先知", "金属巨龙-5", "凤凰-5");
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
        SkillTestContext context = prepare(50, 50, "占位符+献祭", "占位符+免疫");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        // 被免疫
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
    }

    /**
     * 被复活的回合无法献祭
     */
    @Test
    public void test献祭_复活() {
        SkillTestContext context = prepare(50, 50, "占位符+复活", "占位符+献祭1", "占位符");
        CardInfo c占位符1 = context.addToField(0, 0);
        context.addToGrave(1, 0);
        context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 复活
        context.proceedOneRound();
        // 本回合刚被复活，无法献祭
        Assert.assertEquals(2, context.getPlayer(0).getField().size());

        context.proceedOneRound();

        random.addNextPicks(0); // 献祭
        context.proceedOneRound();
        // 占位符1被献祭
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c占位符1.isDead());
    }
    
    /**
     * 被献祭的卡能发动死契技能，也能转生
     */
    @Test
    public void test献祭_死契() {
        SkillTestContext context = prepare(50, 50, "占位符+献祭1", "幽灵巨鲸");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭幽灵巨鲸
        random.addNextNumbers(0); // 转生成功
        context.proceedOneRound();

        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(1, context.getPlayer(0).getHand().size());
        Assert.assertEquals(0, context.getPlayer(0).getGrave().size());
    }

    /**
     * 献祭和时光倒流是同一个优先级的技能，谁在先就谁先发动
     */
    @Test
    public void test献祭_时光倒流() {
        SkillTestContext context = prepare(50, 50, "占位符+献祭", "占位符+时光倒流");
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
        SkillTestContext context = prepare(50, 50, "占位符+时光倒流", "占位符+献祭");
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
        SkillTestContext context = prepare(50, 50, "秘银巨石像+献祭8", "占位符", "隐世先知");
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
        SkillTestContext context = prepare(50, 50, "秘银巨石像+献祭8", "隐世先知");
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
        SkillTestContext context = prepare(
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
     * 降临技能在种族守护之后触发，但还是一张一张卡结算
     */
    @Test
    public void test被动种族守护_降临暴风雪_法力反射_同时上场() {
        SkillTestContext context = prepare(50, 50,
            "残血王国小兵+降临暴风雪1", "占位符+王国守护10", "元素灵龙");
        CardInfo c残血王国小兵 = context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        context.startGame();
        
        random.addNextPicks(0).addNextNumbers(1000); // 降临暴风雪
        context.proceedOneRound();

        // 残血王国小兵发动降临暴风雪时，占位符还未上场，所以被反射死了
        Assert.assertTrue(c残血王国小兵.isDead());
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(0, 5000 - c占位符.getHP());
    }

    /**
     * 种族守护比献祭优先级高，而且献祭的体力增幅计算不包含种族守护,
     * 计算方法为：基础*献祭系数
     */
    // 此为魔卡BUG，而且影响不大，暂时先不测了
    //@Test
    public void test种族守护_献祭_离场() {
        SkillTestContext context = prepare(50, 50, "邪灵女巫-0", "堕落精灵+地狱守护5");
        CardInfo c邪灵女巫 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.startGame();

        random.addNextPicks(1); // 献祭
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(c邪灵女巫.getUniqueName(), context.getPlayer(0).getField().getCard(0).getUniqueName());
        Assert.assertEquals(1050, c邪灵女巫.getMaxHP());
    }

    @Test
    public void test神圣守护_基础() {
        SkillTestContext context = prepare(50, 50, "占位符", "占位符+神圣守护7", "占位符", "占位符");
        CardInfo c占位符1 = context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符3 = context.addToHand(2, 0).setSummonDelay(0);
        context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(5350, c占位符1.getHP());
        Assert.assertEquals(5350, c占位符1.getMaxHP());
        Assert.assertEquals(5350, c占位符2.getHP());
        Assert.assertEquals(5350, c占位符2.getMaxHP());
        Assert.assertEquals(5350, c占位符3.getHP());
        Assert.assertEquals(5350, c占位符3.getMaxHP());
    }

    @Test
    public void test神圣守护_隔轮被动() {
        SkillTestContext context = prepare(50, 50, "占位符+神圣守护7", "占位符", "占位符");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c占位符2 = context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(5350, c占位符1.getHP());
        Assert.assertEquals(5350, c占位符1.getMaxHP());
        Assert.assertEquals(5350, c占位符2.getHP());
        Assert.assertEquals(5350, c占位符2.getMaxHP());
    }

    /**
     * 由于神圣守护卡右边的卡牌死亡而得以靠在右边的卡牌无法获得神圣守护效果
     */
    @Test
    public void test神圣守护_整理靠近() {
        SkillTestContext context = prepare(50, 50, "占位符+神圣守护7", "残血王国小兵", "占位符", "占位符", "秘银巨石像");
        CardInfo c占位符1 = context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c残血王国小兵 = context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToHand(2, 0).setSummonDelay(0);
        context.addToField(3, 1);
        context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(5350, c占位符1.getHP());
        Assert.assertEquals(5350, c占位符1.getMaxHP());
        Assert.assertEquals(351, c残血王国小兵.getHP());
        Assert.assertEquals(351, c残血王国小兵.getMaxHP());
        Assert.assertEquals(5000, c占位符2.getHP());
        Assert.assertEquals(5000, c占位符2.getMaxHP());

        context.proceedOneRound();
        Assert.assertEquals(5350, c占位符1.getHP());
        Assert.assertEquals(5350, c占位符1.getMaxHP());
        Assert.assertTrue(c残血王国小兵.isDead());
        Assert.assertEquals(5000, c占位符2.getHP());
        Assert.assertEquals(5000, c占位符2.getMaxHP());
    }

    /**
     * 降临技能优先于献祭
     */
    @Test
    public void test降临烈焰风暴_献祭() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+献祭8", "占位符+降临烈焰风暴1", "占位符");
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
     * 被降临复活拉回来的卡照常发动降临系技能，仍然遵从[主动BUFF]->[被动BUFF]->[降临系技能]的顺序
     */
    @Test
    public void test降临复活和BUFF_复活的降临系技能() {
        SkillTestContext context = prepare(50, 50, "月蚀兽+王国守护10", "残血王国小兵+降临烈焰风暴1", "占位符+法力反射1");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c残血王国小兵 = context.addToGrave(1, 0);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 降临全体阻碍
        random.addNextPicks(0); // 降临复活
        random.addNextPicks(0).addNextNumbers(0); // 降临烈焰风暴
        context.proceedOneRound();
        // 由于BUFF比降临烈焰风暴先发动，残血王国小兵的血量提升，所以被法力反射打后并没死
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertFalse(c残血王国小兵.justRevived());  // 降临复活拉回来的卡立刻就能行动
        Assert.assertEquals(30, 1 + 500 - c残血王国小兵.getHP());
        Assert.assertEquals(825, 5000 - c占位符.getHP());
    }


    /**
     * 被降临复活拉回来的卡照常发动降临系技能，仍然遵从[主动BUFF]->[被动BUFF]->[降临系技能]的顺序
     */
    @Test
    public void test降临复活_复活的降临系技能() {
        SkillTestContext context = prepare(50, 50, "月蚀兽", "秘银巨石像+降临烈焰风暴1", "占位符*2");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c秘银巨石像 = context.addToGrave(1, 0);
        CardInfo c占位符1 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1); 
        context.startGame();

        random.addNextPicks(0, 1); // 降临全体阻碍
        random.addNextPicks(0); // 降临复活
        random.addNextPicks(0, 1).addNextNumbers(0, 0); // 降临烈焰风暴
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertFalse(c秘银巨石像.justRevived());  // 降临复活拉回来的卡立刻就能行动
        Assert.assertEquals(0, 1550 - c秘银巨石像.getHP());
        Assert.assertEquals(655 + 25, 5000 - c占位符1.getHP());
        Assert.assertEquals(810 + 25, 5000 - c占位符2.getHP());
    }

    /**
     * 即使在受控的状况下，依然可以使用神性祈求
     */
    @Test
    public void test神性祈求_封印() {
        SkillTestContext context = prepare(
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
    
    @Test
    public void test星云锁链_普通() {
        SkillTestContext context = prepare(
            50, 50, "占位符+星云锁链", "占位符", "城镇弓箭兵", "攻城弩车手", "秘银巨石像");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToDeck(1, 0);
        context.addToDeck(2, 0);
        context.addToDeck(3, 0);
        CardInfo c秘银巨石像 = context.addToDeck(4, 0);
        context.getEngine().getStage().getRule().setDeckOrder(1);
        context.startGame();

        // 秘银巨石像被星云锁链召唤
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getField().getCard(1).getUniqueName());
    }

    /**
     * 需要确认并修复
     */
    @Test
    public void test星云锁链_多次() {
        SkillTestContext context = prepare(
            50, 50, "占位符+星云锁链*2", "占位符", "城镇弓箭兵", "攻城弩车手", "秘银巨石像");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToDeck(2, 0);
        context.addToDeck(3, 0);
        CardInfo c攻城弩车手 = context.addToDeck(4, 0);
        CardInfo c秘银巨石像 = context.addToDeck(5, 0);
        context.getEngine().getStage().getRule().setDeckOrder(1);
        context.startGame();

        // 秘银巨石像被星云锁链召唤
        context.proceedOneRound();
        Assert.assertEquals(4, context.getPlayer(0).getField().size());
        Assert.assertEquals(c秘银巨石像.getUniqueName(), context.getPlayer(0).getField().getCard(1).getUniqueName());
        Assert.assertEquals(c攻城弩车手.getUniqueName(), context.getPlayer(0).getField().getCard(3).getUniqueName());
    }

    @Test
    public void test星云锁链_降临复活() {
        SkillTestContext context = prepare(
                50, 50, "占位符+星云锁链", "占位符", "占位符+降临复活", "占位符", "占位符");
        CardInfo c占位符1 = context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToDeck(1, 0);
        CardInfo c占位符3 = context.addToDeck(2, 0);
        CardInfo c占位符4 = context.addToGrave(3, 0);
        context.addToField(4, 1);
        context.getEngine().getStage().getRule().setDeckOrder(1);
        context.startGame();

        context.proceedOneRound();
        Player p1 = c占位符1.getOwner();
        Assert.assertEquals(2, p1.getField().size());
        Assert.assertSame(c占位符1, p1.getField().getCard(0));
        Assert.assertSame(c占位符3, p1.getField().getCard(1));
        Assert.assertEquals(1, p1.getHand().size());
        Assert.assertSame(c占位符2, p1.getHand().getCard(0));
        Assert.assertEquals(1, p1.getGrave().size());
        Assert.assertSame(c占位符4, p1.getGrave().getFirst());
    }

    @Test
    public void test咆哮_基础() {
        SkillTestContext context = prepare(50, 50, "占位符+咆哮", "占位符*4");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToField(1, 1);
        CardInfo c占位符3 = context.addToField(2, 1);
        CardInfo c占位符4 = context.addToHand(3, 1).setSummonDelay(3);
        CardInfo c占位符5 = context.addToHand(4, 1).setSummonDelay(4);
        context.startGame();

        random.addNextPicks(1); // 咆哮杀死占位符3
        context.proceedOneRound();
        Assert.assertEquals(5000, c占位符2.getHP());
        Assert.assertTrue(c占位符3.isDead());
        Assert.assertEquals(1, c占位符4.getOwner().getHand().size());
        Assert.assertTrue(c占位符5.isDead());
    }
}
