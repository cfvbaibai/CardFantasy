package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SummonTest extends SkillValidationTest {
    /**
     * 召唤王国战士基本测试
     */
    @Test
    public void test召唤王国战士() {
        SkillTestContext context = prepare(50, 50, "陨星魔法使", "占位符*3");
        context.addToField(0, 0);
        CardInfo c1 = context.addToField(1, 1);
        CardInfo c2 = context.addToField(2, 1);
        CardInfo c3 = context.addToField(3, 1);
        context.startGame();

        random.addNextPicks(0, 1, 2).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(3, context.getPlayer(0).getField().size());
        // 255 来自寒霜冲击
        Assert.assertEquals(745 + 255, 5000 - c1.getHP());
        Assert.assertEquals(255, 5000 - c2.getHP()); // 被召唤的卡在被召唤的那一轮不能攻击
        Assert.assertEquals(255, 5000 - c3.getHP());

        context.proceedOneRound();

        random.addNextPicks(0, 1, 2).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(745 + 745 + 255 + 255, 5000 - c1.getHP());
        Assert.assertEquals(395 + 255 + 255, 5000 - c2.getHP());
        Assert.assertEquals(225 + 255 + 255, 5000 - c3.getHP());
    }
    
    /**
     * 被召唤的卡死亡时，处理离场效果，不进入墓地
     */
    @Test
    public void test召唤王国战士_被召唤卡死亡() {
        SkillTestContext context = prepare(50, 50, "陨星魔法使", "占位符", "金属巨龙");
        CardInfo c陨星魔法师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0, 1).addNextNumbers(1000);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(3, context.getPlayer(0).getField().size());
        Assert.assertEquals(1850 + 200 /* 召唤圣骑士的王国守护 */, c陨星魔法师.getHP());

        random.addNextNumbers(0); // 金属巨龙暴击成功
        context.proceedOneRound();
        Assert.assertEquals(1850, c陨星魔法师.getHP()); // 圣骑士离场，失去BUFF

        random.addNextPicks(0, 1).addNextNumbers(1000);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size()); // 召唤的两张卡死了一张，此时仍不能召唤新卡
        Assert.assertEquals(0, context.getPlayer(0).getGrave().size()); // 被召唤的卡死亡后不进入墓地
    }
    
    @Test
    public void test召唤王国战士_送还() {
        SkillTestContext context = prepare(50, 50, "陨星魔法使", "占位符", "降临天使");
        context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0, 1).addNextNumbers(1000, 1000);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertEquals(0, context.getPlayer(0).getDeck().size()); // 被召唤的卡被送还后不进入卡组
    }

    @Test
    public void test召唤王国战士_多张陨星() {
        SkillTestContext context = prepare(50, 50, "陨星魔法使*2", "占位符*2", "金属巨龙", "占位符", "金属巨龙");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 1);
        context.addToField(3, 1);
        CardInfo c金属巨龙1 = context.addToField(4, 1);
        context.addToField(5, 1);
        CardInfo c金属巨龙2 = context.addToField(6, 1);
        context.startGame();

        random.addNextPicks(0, 1, 2, 3, 4).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使1寒霜冲击
        random.addNextPicks(0, 1, 2, 3, 4).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使2寒霜冲击
        Field fieldA = context.getPlayer(0).getField();
        context.proceedOneRound();
        Assert.assertEquals(6, fieldA.size()); // 两个陨星魔法使各召唤2张卡

        random.addNextNumbers(0, 0); // 两个金属巨龙都暴击成功, 每个陨星魔法使都被杀死一张仆从
        context.proceedOneRound();
        Assert.assertEquals(4, fieldA.size());

        c金属巨龙1.reset(); // 回血免得被打死
        c金属巨龙2.reset(); // 回血免得被打死
        random.addNextPicks(0, 1, 2, 3, 4).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使1寒霜冲击
        random.addNextPicks(0, 1, 2, 3, 4).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使2寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(4, fieldA.size()); // 此时任何一张陨星魔法使都不能再召唤

        random.addNextNumbers(0); // 金属巨龙1暴击，杀死陨星魔法使1的仆从，金属巨龙2直接攻击英雄
        context.proceedOneRound();
        Assert.assertEquals(3, fieldA.size());

        c金属巨龙1.reset(); // 回血免得被打死
        c金属巨龙2.reset(); // 回血免得被打死
        random.addNextPicks(0, 1, 2, 3, 4).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使1寒霜冲击
        random.addNextPicks(0, 1, 2, 3, 4).addNextNumbers(1000, 1000, 1000);  // 陨星魔法使2寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(3, fieldA.size()); // 陨星魔法使1的仆从全灭，仍然不能再次召唤
    }

    /**
     * 召唤技能类似背刺、封印，属于一次性技能
     */
    @Test
    public void test召唤王国战士_复活再召唤() {
        SkillTestContext context = prepare(50, 50, "陨星魔法使", "复活者", "金属巨龙+暴击6-15", "女神侍者-0");
        context.addToField(0, 0);
        context.startGame();

        Field fieldA = context.getPlayer(0).getField();
        context.proceedOneRound();
        Assert.assertEquals(3, fieldA.size()); // 召唤两个仆从

        context.addToField(2, 1);   // 金属巨龙上场
        random.addNextNumbers(0, 0); // 金属巨龙暴击+暴击，杀死陨星魔法使
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());

        context.addToField(1, 0); // 复活者上场
        random.addNextPicks(0); // 复活陨星魔法使
        context.proceedOneRound();
        Assert.assertEquals(4, fieldA.size()); // 现在无法重新召唤
        
        random.addNextNumbers(0, 0); // 金属巨龙暴击杀死圣骑士
        context.proceedOneRound();
        Assert.assertEquals(3, fieldA.size());
        
        random.addNextPicks(0);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(3, fieldA.size()); // 复活者无法复活仆从，陨星魔法使也暂时无法重新召唤

        context.addToField(3, 1); // 女神侍者上场
        random.addNextNumbers(0, 0); // 金属巨龙暴击杀死魔剑士
        random.addNextPicks(2).addNextNumbers(0); // 女神侍者冰冻陨星魔法使
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size()); // 此时陨星魔法使被冰冻，还是不能召唤

        random.addNextNumbers(1000, 1000); // 金属巨龙不暴击
        random.addNextPicks(0).addNextNumbers(1000); // 女神侍者不冰冻
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
        
        random.addNextPicks(0, 1).addNextNumbers(1000);  // 陨星魔法使寒霜冲击
        context.proceedOneRound();
        Assert.assertEquals(4, fieldA.size()); // 陨星魔法使可以行动了，召唤
    }
    
    /**
     * 召唤不插空位，总是召唤在后面
     */
    @Test
    public void test召唤王国战士_前有空位() {
        SkillTestContext context = prepare(50, 50, "占位符", "陨星魔法使", "占位符*2", "雷盾");
        context.addToField(0, 0).setBasicHP(1);
        context.addToField(1, 0);
        context.addToField(2, 1);
        context.addToField(3, 1);
        RuneInfo r雷盾 = context.addToRune(0, 1);
        context.startGame();

        r雷盾.activate();
        random.addNextPicks(0, 1).addNextNumbers(0, 0); // 陨星魔法使的寒霜冲击
        context.proceedOneRound();
        Field fieldA = context.getPlayer(0).getField();
        Assert.assertEquals(3, fieldA.size());
        // 首先占位者被雷盾弹死，然后陨星魔法使召唤2卡分别在3号位和4号位
        // 所以陨星魔法使触发的雷盾不会攻击到4号位的魔剑士
        Assert.assertEquals(-200 /* 圣骑士的王国守护 */, 1240 - fieldA.getCard(2).getHP());
    }
    
    /**
     * 召唤物不能被秽土转生
     */
    @Test
    public void test召唤王国战士_秽土() {
        SkillTestContext context = prepare(50, 50, "陨星魔法使", "秽土", "占位符", "金属巨龙+暴击10", "蜘蛛人女王*3");
        context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.addToRune(0, 0);
        context.startGame();
        
        // 加3张蛮荒去墓地发动秽土
        context.addToGrave(3, 0);
        context.addToGrave(4, 0);
        context.addToGrave(5, 0);
        random.addNextPicks(0, 1).addNextNumbers(1000, 1000);      // 陨星魔法使的寒霜冲击无法冻结
        context.proceedOneRound();
        
        random.addNextNumbers(0, 0);    // 金属巨龙双暴击
        random.addNextNumbers(0);       // 假设秽土可以转生
        context.proceedOneRound();
        // 秽土即使能发动，也不能对召唤物发动。手牌仍为0
        Assert.assertEquals(0, context.getPlayer(0).getHand().size());
        Assert.assertEquals(3, context.getPlayer(0).getGrave().size());
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
    }

    @Test
    public void test召唤王国战士_灵魂消散() {
        SkillTestContext context = prepare(50, 50, "残血王国小兵+召唤王国战士", "占位符+灵魂消散");
        CardInfo c小兵 = context.addToHand(0, 0).setSummonDelay(0);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(3, c小兵.getOwner().getField().size());
        Assert.assertEquals(201, c小兵.getHP());

        context.proceedOneRound();
        Assert.assertEquals(1, c小兵.getOwner().getField().size());
        Assert.assertFalse(c小兵.isDead());
        Assert.assertEquals(1, c小兵.getHP());
    }

    @Test
    public void test灵魂消散_魔王() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+灵魂消散", "大毒汁怪");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 0);
        CardInfo c大毒汁怪 = context.addToField(2, 1).setBasicHP(2);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, c大毒汁怪.getOwner().getField().size());
        Assert.assertEquals(1, c大毒汁怪.getOwner().getGrave().size());
        Assert.assertTrue(c大毒汁怪.isDead());
        Assert.assertEquals(0, c大毒汁怪.getOwner().getOutField().size());
        Assert.assertEquals("炎魔", c大毒汁怪.getOwner().getField().getCard(0).getName());
    }
}
