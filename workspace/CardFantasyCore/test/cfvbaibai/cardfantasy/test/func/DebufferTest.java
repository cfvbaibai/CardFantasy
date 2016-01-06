package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.RuneInfo;

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

        int expectedLife = (int) (1300 - (int) (1300 * 0.10));
        int expectedAttack = (int) (340 - (int) (340 * 0.10));

        Assert.assertEquals(expectedLife, c凤凰.getHP());
        Assert.assertEquals(expectedAttack, c凤凰.getCurrentAT());
    }

    /**
     * 种族buffer增加的血量攻击不加入被削弱的比例中
     */
    @Test
    public void test凋零真言_种族buffer() {
        SkillTestContext context = prepare(100, 100, "占位符+凋零真言6-15", "魔剑士-0",
                "占位符+王国守护5-15", "占位符+王国之力5-15");
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

        // 出场了

        int expectedLife = (int) (820 + 250 - (int) (820 * 0.10));
        int expectedAttack = (int) (125 + 125 - (int) (125 * 0.10));

        Assert.assertEquals(expectedLife, c魔剑.getHP());
        Assert.assertEquals(expectedAttack, c魔剑.getCurrentAT());
    }

    /**
     * 邪吸后的攻击力会被计算在削弱比例中
     */
    @Test
    public void test凋零真言_邪吸() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+凋零真言6-15",
                "末日预言师-15");
        context.addToField(0, 0);
        CardInfo c末日 = context.addToField(1, 1);
        context.startGame();

        for (int i = 0; i < 3; i++) {
            context.proceedOneRound();
        }

        // 出场了
        int firstRoundAttack1 = (int) ((int) (795 - (int) (795 * 0.10)) + (int) (810 * 0.18));
        int firstRoundAttack2 = 810 - (int) (810 * 0.18);
        int secondRoundAttack1 = (int) ((int) (firstRoundAttack1 - (int) (firstRoundAttack1 * 0.10)) + (int) (firstRoundAttack2 * 0.18));

        Assert.assertEquals(secondRoundAttack1, c末日.getCurrentAT());
    }

    /**
     * 对免疫无效
     */
    @Test
    public void test凋零真言_免疫() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+凋零真言6-15",
                "光明之龙-15");
        context.addToField(0, 0);
        CardInfo c光龙 = context.addToField(1, 1);
        context.startGame();

        for (int i = 0; i < 1; i++) {
            random.addNextNumbers(0);
            context.proceedOneRound();
        }
        // 出场了

        Assert.assertEquals(770, c光龙.getCurrentAT());
    }

    /**
     * 对免疫有效，且破除掉其冰甲邪吸技能,
     */
    @Test
    public void test沉默_免疫() {
        SkillTestContext context = prepare(100, 100, "末日预言师+免疫-15", "灵魂收割者+降临沉默-15");
        CardInfo c末日 = context.addToHand(0, 0);
        CardInfo c萝莉 = context.addToHand(1, 1);

        context.startGame();

        for (int i = 0; i < 6; i++) {
            random.addNextNumbers(0);
            context.proceedOneRound();
        }
        // 出场了
        boolean IsLocked = c末日.getStatus().containsStatus(CardStatusType.锁定);
        int expectedLife = 2010 - 755;
        int expectedAttack = 755;

        Assert.assertEquals(true, IsLocked);
        Assert.assertEquals(expectedLife, c末日.getHP());
        Assert.assertEquals(expectedAttack, c萝莉.getCurrentAT());
    }

    /**
     * 被沉默后，鬼步效果也无法发动
     */
    @Test
    public void test沉默_鬼步() {
        SkillTestContext context = prepare(100, 100, "末日预言师+免疫-15",
                "末日预言师+免疫-15", "灵魂收割者+降临沉默-15", "鬼步-4");
        CardInfo c末日1 = context.addToHand(0, 0);
        context.addToHand(1, 0);
        context.addToHand(2, 1);

        context.addToRune(0, 0);

        context.startGame();

        for (int i = 0; i < 6; i++) {
            random.addNextNumbers(0);
            context.proceedOneRound();
        }
        // 出场了
        boolean isLocked = c末日1.getStatus().containsStatus(CardStatusType.锁定);
        int expectedLife = 2010 + 300 - 755;

        Assert.assertTrue(isLocked);
        Assert.assertEquals(expectedLife, c末日1.getHP());
    }

    /**
     * 沉默持续到对方回合结束,
     */
    @Test
    public void test沉默_持续回合() {
        SkillTestContext context = prepare(100, 100, "黄金金属巨龙+冰甲5-15",
                "末日预言师+降临沉默-15");
        context.addToField(0, 0);
        CardInfo c末日 = context.addToHand(1, 1);

        context.startGame();

        for (int i = 0; i < 7; i++) {
            context.proceedOneRound();
        }
        // 出场了
        int expectedLife1 = 2010 - 140;
        int NextRoundAttack = (int) (805 - (int) (805 * 0.18) + 140 + 795);
        int expectedLife2 = expectedLife1 - NextRoundAttack;

        Assert.assertEquals(expectedLife2, c末日.getHP());
    }

    /**
     * 被沉默的回合回春月恩也无法施放
     */
    @Test
    public void test沉默_回春() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+沉默", "占位符+回春10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);

        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.getStatus().containsStatus(CardStatusType.沉默));

        context.proceedOneRound();
        Assert.assertFalse(c占位符.getStatus().containsStatus(CardStatusType.沉默));
        Assert.assertEquals(810, 5000 - c占位符.getHP());
    }

    /**
     * 沉默无法被净化
     */
    @Test
    public void test沉默_净化() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+沉默", "福音乐师");
        context.addToField(0, 0);
        CardInfo c福音乐师= context.addToField(1, 1);

        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c福音乐师.getStatus().containsStatus(CardStatusType.沉默));
        Assert.assertEquals(810, 1610 - c福音乐师.getHP());

        context.proceedOneRound();
        Assert.assertFalse(c福音乐师.getStatus().containsStatus(CardStatusType.沉默));
        Assert.assertEquals(810, 1610 - c福音乐师.getHP());
    }

    /**
     * 被沉默后无法转生
     */
    @Test
    public void test沉默_转生() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+沉默", "占位符+转生10");
        context.addToField(0, 0);
        CardInfo c占位符= context.addToField(1, 1).setBasicHP(2);

        context.startGame();

        random.addNextNumbers(0); // 假设转生成功
        context.proceedOneRound();
        Assert.assertEquals(0, c占位符.getOwner().getField().size());
        Assert.assertEquals(0, c占位符.getOwner().getHand().size());
        Assert.assertEquals(1, c占位符.getOwner().getGrave().size());
    }

    /**
     * 被沉默后无法发动死契技能
     */
    @Test
    public void test沉默_死契技能() {
        SkillTestContext context = prepare(100, 100, "魔剑士+沉默", "占位符+死契火球1");
        CardInfo c魔剑士 = context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);

        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 死契火球1
        context.proceedOneRound();
        Assert.assertEquals(0, c占位符.getOwner().getField().size());
        Assert.assertEquals(0, c占位符.getOwner().getHand().size());
        Assert.assertEquals(1, c占位符.getOwner().getGrave().size());
        Assert.assertEquals(0, 1450 - c魔剑士.getHP());
    }

    /**
     * 被沉默后无法发动燕返和不屈
     */
    @Test
    public void test沉默_燕返_不屈() {
        SkillTestContext context = prepare(100, 100, "魔剑士+沉默", "欲望惩罚者");
        CardInfo c魔剑士 = context.addToField(0, 0);
        CardInfo c欲望惩罚者 = context.addToField(1, 1).setBasicHP(2);

        context.startGame();

        context.proceedOneRound();
        Assert.assertFalse(c魔剑士.isDead());
        Assert.assertEquals(0, 1450 - c魔剑士.getHP());
        Assert.assertTrue(c欲望惩罚者.isDead());
    }

    /**
     * 被沉默后，防御类符文也无法触发
     */
    @Test
    public void test沉默_防御符文() {
        SkillTestContext context = prepare(100, 100, "魔剑士+沉默", "魔剑士", "金属巨龙*2", "雷盾");
        CardInfo c魔剑士1 = context.addToField(0, 0);
        CardInfo c魔剑士2 = context.addToField(1, 0);
        CardInfo c金属巨龙1 = context.addToField(2, 1);
        CardInfo c金属巨龙2 = context.addToField(3, 1);
        RuneInfo r雷盾 = context.addToRune(0, 1);

        context.startGame();
        r雷盾.activate();
        context.proceedOneRound();
        Assert.assertEquals(200, 1450 - c魔剑士1.getHP());
        Assert.assertEquals(200, 1240 - c魔剑士2.getHP());
        Assert.assertEquals(275, 1710 - c金属巨龙1.getHP());
        Assert.assertEquals(65, 1710 - c金属巨龙2.getHP());
    }

    /**
     * 被沉默后，攻击BUFF符文也无法触发
     */
    @Test
    public void test沉默_攻击BUFF符文() {
        SkillTestContext context = prepare(100, 100, "占位符+沉默", "秘银巨石像", "堕落精灵*3", "绝杀");
        CardInfo c占位符 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.addToGrave(2, 1);
        context.addToGrave(3, 1);
        context.addToGrave(4, 1);
        context.addToRune(0, 1);

        context.startGame();
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.沉默));
        context.proceedOneRound();
        Assert.assertEquals(660, 5000 - c占位符.getHP());
    }

    /**
     * 被沉默后，秽土也无法触发
     */
    @Test
    public void test沉默_秽土() {
        SkillTestContext context = prepare(100, 100, "秘银巨石像+沉默", "占位符", "秽土");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        RuneInfo r秽土 = context.addToRune(0, 1);

        context.startGame();
        r秽土.activate();
        random.addNextNumbers(0); // 假设秽土转生成功
        context.proceedOneRound();
        Assert.assertEquals(0, c占位符.getOwner().getField().size());
        Assert.assertEquals(0, c占位符.getOwner().getHand().size());
        Assert.assertEquals(1, c占位符.getOwner().getGrave().size());
    }

    /**
     * 龙吟不受沉默影响
     */
    @Test
    public void test沉默_龙吟() {
        SkillTestContext context = prepare(100, 100, "占位符+沉默", "秘银巨石像", "龙吟");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        c秘银巨石像.getOwner().setHP(2);
        context.addToRune(0, 1);

        context.startGame();
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.沉默));
        context.proceedOneRound();
        Assert.assertEquals(1150 + 2, c秘银巨石像.getOwner().getHP());
    }
}
