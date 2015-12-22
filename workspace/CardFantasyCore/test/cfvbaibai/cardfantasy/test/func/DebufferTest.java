package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;

public class DebufferTest extends SkillValidationTest {
	
    /**
     * 普通卡牌削弱
     */
    @Test
    public void test凋零真言_普通() {
        SkillTestContext context = prepare(100, 100, "占位符+凋零真言6-15", "凤凰-0");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        
        context.startGame();

        context.proceedOneRound();
        
        int expectedLife = (int)(1300 - (int)(1300* 0.10));
        int expectedAttack = (int)(340 - (int)(340 * 0.10));


        
        Assert.assertEquals(expectedLife, c凤凰.getHP());
        Assert.assertEquals(expectedAttack, c凤凰.getCurrentAT());
    }
	
    /**
     * 种族buffer增加的血量攻击不加入被削弱的比例中
     */
    @Test
    public void test凋零真言_种族buffer() {
        SkillTestContext context = prepare(100, 100, "占位符+凋零真言6-15", "魔剑士-0","占位符+王国守护5-15","占位符+王国之力5-15");
        context.addToField(0, 0);
        CardInfo c魔剑 = context.addToHand(1, 1);
        context.addToHand(2, 1);
        context.addToHand(3, 1);
        context.startGame();

        context.proceedOneRound();
        context.proceedOneRound();
        context.proceedOneRound();
        context.proceedOneRound();
        context.proceedOneRound();
        context.proceedOneRound();

        //出场了
        
        int expectedLife = (int)(820 + 250 - (int)(820* 0.10));
        int expectedAttack = (int)(125 + 125 - (int)(125 * 0.10));


        
        Assert.assertEquals(expectedLife, c魔剑.getHP());
        Assert.assertEquals(expectedAttack, c魔剑.getCurrentAT());
    }
    
    /**
     * 邪吸后的攻击力会被计算在削弱比例中
     */
    @Test
    public void test凋零真言_邪吸() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+凋零真言6-15", "末日预言师-15");
        context.addToField(0, 0);
        CardInfo c末日 = context.addToField(1, 1);
        context.startGame();

        for(int i=0;i<3;i++)
        {
        	context.proceedOneRound();
        }

        //出场了
        int firstRoundAttack1 = (int)((int)(795 - (int)(795 * 0.10)) + (int)(810*0.18));
        int firstRoundAttack2 =  810 - (int)(810*0.18);
        int secondRoundAttack1 = (int)((int)(firstRoundAttack1 - (int)(firstRoundAttack1 * 0.10)) + (int)(firstRoundAttack2*0.18));
        
        Assert.assertEquals(secondRoundAttack1, c末日.getCurrentAT());
    }
    
    /**
     * 对免疫无效
     */
    @Test
    public void test凋零真言_免疫() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+凋零真言6-15", "光明之龙-15");
        context.addToField(0, 0);
        CardInfo c光龙 = context.addToField(1, 1);
        context.startGame();

        for(int i=0;i<1;i++)
        {
            random.addNextNumbers(0); 
        	context.proceedOneRound();
        }
        //出场了
   
        Assert.assertEquals(770, c光龙.getCurrentAT());
    }
    
    /**
     * 对免疫有效，且破除掉其冰甲邪吸技能,
     */
    @Test
    public void test沉默_免疫() {
        SkillTestContext context = prepare(100, 100, "末日预言师+免疫-15","灵魂收割者+降临沉默-15");
        CardInfo c末日 = context.addToHand(0, 0);
        CardInfo c萝莉 = context.addToHand(1, 1);

        
        context.startGame();

        for(int i=0;i<6;i++)
        {
            random.addNextNumbers(0); 
        	context.proceedOneRound();
        }
        //出场了
        boolean IsLocked = c末日.getStatus().containsStatus(CardStatusType.锁定);
        int expectedLife = 2010 - 755;
        int expectedAttack = 755;
   
        Assert.assertEquals(true, IsLocked);
        Assert.assertEquals(expectedLife, c末日.getHP());
        Assert.assertEquals(expectedAttack, c萝莉.getCurrentAT());
    }
    
    /**
     * 无法破除符文的技能，譬如鬼步
     */
    @Test
    public void test沉默_鬼步() {
        SkillTestContext context = prepare(100, 100, "末日预言师+免疫-15","末日预言师+免疫-15","灵魂收割者+降临沉默-15","鬼步-4");
        CardInfo c末日1 = context.addToHand(0, 0);
        CardInfo c末日2 = context.addToHand(1, 0);
        CardInfo c萝莉 = context.addToHand(2, 1);
        
        context.addToRune(0, 0);

        
        context.startGame();

        for(int i=0;i<6;i++)
        {
            random.addNextNumbers(0); 
        	context.proceedOneRound();
        }
        //出场了
        boolean IsLocked = c末日1.getStatus().containsStatus(CardStatusType.锁定);
        int expectedLife = 2010 + 300 - 755;
   
        Assert.assertEquals(false, IsLocked);
        Assert.assertEquals(expectedLife, c末日1.getHP());
    }

    
    /**
     * 沉默持续到对方回合结束,
     */
    @Test
    public void test沉默_持续回合() {
        SkillTestContext context = prepare(100, 100, "黄金金属巨龙+冰甲5-15","末日预言师+降临沉默-15");
        CardInfo c大便龙 = context.addToField(0, 0);
        CardInfo c末日 = context.addToHand(1, 1);

        
        context.startGame();

        for(int i=0;i<7;i++)
        {
        	context.proceedOneRound();
        }
        //出场了
        int expectedLife1 = 2010 - 140;
        int NextRoundAttack = (int)(805 - (int)( 805* 0.18 ) + 140 + 795);
        int expectedLife2 = expectedLife1 - NextRoundAttack;
   
        Assert.assertEquals(expectedLife2, c末日.getHP());

    }

}
