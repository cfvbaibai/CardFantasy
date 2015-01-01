package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;

public class DelayTest extends SkillValidationTest {
    @Test
    public void test阻碍_奇数() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "死兆星", "淘气灯灵");
        context.addToField(0, 0);
        CardInfo c淘气灯灵 = context.addToHand(1, 1);
        Field fieldB = context.getPlayer(1).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(1, c淘气灯灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c淘气灯灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c淘气灯灵.getSummonDelay());

        random.addNextPicks(0).addNextNumbers(0);
        context.proceedOneRound();
        Assert.assertEquals(1, fieldB.size());
    }

    @Test
    public void test阻碍_偶数() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "死兆星", "堕落精灵");
        context.addToField(0, 0);
        CardInfo c堕落精灵 = context.addToHand(1, 1);
        Field fieldB = context.getPlayer(1).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(2, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(1, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(1, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(0, fieldB.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        random.addNextNumbers(0, 0);
        context.proceedOneRound();
        Assert.assertEquals(1, fieldB.size());
    }
    
    /**
     * 阻碍靠前的卡
     */
    @Test
    public void test阻碍_相同等待时间() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "死兆星", "堕落精灵*2");
        context.addToField(0, 0);
        CardInfo c堕落精灵1 = context.addToHand(1, 1);
        CardInfo c堕落精灵2 = context.addToHand(2, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(2, c堕落精灵1.getSummonDelay());
        Assert.assertEquals(1, c堕落精灵2.getSummonDelay());
    }
    
    /**
     * 阻碍等待时间最短的卡，哪怕靠后
     */
    @Test
    public void test阻碍_不同等待时间() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "死兆星", "堕落精灵*2");
        context.addToField(0, 0);
        CardInfo c堕落精灵1 = context.addToHand(1, 1).setSummonDelay(4);
        CardInfo c堕落精灵2 = context.addToHand(2, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(3, c堕落精灵1.getSummonDelay());
        Assert.assertEquals(2, c堕落精灵2.getSummonDelay());
    }
    
    /**
     * 容错测试
     */
    @Test
    public void test阻碍_无手牌() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "死兆星");
        context.addToField(0, 0);
        context.startGame();

        context.proceedOneRound();
    }

    @Test
    public void test加速_6() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "金属巨龙", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c金属巨龙 = context.addToHand(1, 0).setSummonDelay(6);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(4, c金属巨龙.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(3, c金属巨龙.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(1, c金属巨龙.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c金属巨龙.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_5() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "凤凰", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToHand(1, 0).setSummonDelay(5);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(3, c凤凰.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(2, c凤凰.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c凤凰.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c凤凰.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_4() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "凤凰", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToHand(1, 0).setSummonDelay(4);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(2, c凤凰.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(1, c凤凰.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c凤凰.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c凤凰.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_3() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "黑甲铁骑士", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c黑甲铁骑士 = context.addToHand(1, 0).setSummonDelay(3);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(1, c黑甲铁骑士.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c黑甲铁骑士.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_2() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "堕落精灵", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c堕落精灵 = context.addToHand(1, 0).setSummonDelay(2);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_1() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "堕落精灵", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c堕落精灵 = context.addToHand(1, 0).setSummonDelay(1);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c堕落精灵.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_0() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "淘气灯灵", "秘银巨石像");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速_无手牌() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长");
        context.addToField(0, 0);
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
    }

    @Test
    public void test加速2_0() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        context.addToHand(1, 0).setSummonDelay(0);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速2_1() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToHand(1, 0).setSummonDelay(1);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速2_2() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToHand(1, 0).setSummonDelay(2);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    @Test
    public void test加速2_3() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToHand(1, 0).setSummonDelay(3);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }
    
    @Test
    public void test加速2_4() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToHand(1, 0).setSummonDelay(4);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(1, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }
    
    @Test
    public void test加速2_5() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToHand(1, 0).setSummonDelay(5);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(2, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(1, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }
    
    @Test
    public void test加速2_6() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "逐月饿狼", "秘银巨石像", "金属巨龙");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToHand(1, 0).setSummonDelay(6);
        context.addToHand(2, 1).setSummonDelay(100); // 为了防止对手没卡直接胜利
        Field fieldA = context.getPlayer(0).getField();
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(3, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(2, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());

        context.proceedOneRound();
        Assert.assertEquals(1, fieldA.size());
        Assert.assertEquals(0, c秘银巨石像.getSummonDelay());
        
        context.proceedOneRound();
        Assert.assertEquals(2, fieldA.size());
    }

    /**
     * 加速靠前的卡
     */
    @Test
    public void test加速_相同等待时间() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "堕落精灵*2");
        context.addToField(0, 0);
        CardInfo c堕落精灵1 = context.addToHand(1, 0);
        CardInfo c堕落精灵2 = context.addToHand(2, 0);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(0, c堕落精灵1.getSummonDelay());
        Assert.assertEquals(1, c堕落精灵2.getSummonDelay());
    }
    
    /**
     * 加速等待时间最短的卡，哪怕靠后
     */
    @Test
    public void test加速_不同等待时间() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "大图书馆长", "堕落精灵*2");
        context.addToField(0, 0);
        CardInfo c堕落精灵1 = context.addToHand(1, 0);
        CardInfo c堕落精灵2 = context.addToHand(2, 0).setSummonDelay(4);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(1, c堕落精灵1.getSummonDelay());
        Assert.assertEquals(2, c堕落精灵2.getSummonDelay());
    }
}