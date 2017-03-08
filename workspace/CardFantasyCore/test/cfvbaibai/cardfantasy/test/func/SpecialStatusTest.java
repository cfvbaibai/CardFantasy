package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class SpecialStatusTest extends SkillValidationTest {
    /**
     * 被冰冻时无法回春
     */
    @Test
    public void test冰冻_回春() {
        SkillTestContext context = prepare(50, 50, "水源制造者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 水源冰冻凤凰
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(310 + 80, 1560 - c凤凰.getHP());
    }

    /**
     * 被锁定时无法回春
     */
    @Test
    public void test锁定_回春() {
        SkillTestContext context = prepare(50, 50, "地岭拥有者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 地岭拥有者锁定凤凰
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(335, 1560 - c凤凰.getHP());
    }
    
    /**
     * 被锁定时仍能发动盾刺
     */
    @Test
    public void test锁定_盾刺() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+陷阱1", "占位符+盾刺10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 锁定
        context.proceedOneRound();
        Assert.assertEquals(200 /* 盾刺 */, 1550 - c秘银巨石像.getHP());
    }

    /**
     * 被锁定时仍能发动邪灵汲取
     */
    @Test
    public void test锁定_邪灵汲取() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+陷阱1", "占位符+邪灵汲取10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 锁定
        context.proceedOneRound();
        Assert.assertEquals(243 /* 邪灵汲取 */, 810 - c秘银巨石像.getCurrentAT());
    }

    /**
     * 被麻痹时仍能回春
     */
    @Test
    public void test麻痹_回春() {
        SkillTestContext context = prepare(50, 50, "风暴召唤者-5", "凤凰");
        context.addToField(0, 0);
        CardInfo c凤凰 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(0).addNextPicks(0); // 风暴召唤者麻痹凤凰
        context.proceedOneRound();

        random.addNextNumbers(0).addNextPicks(0); // 凤凰烈焰风暴
        context.proceedOneRound();
        Assert.assertEquals(300 + 100 - 210, 1560 - c凤凰.getHP());
    }
    
    /**
     * 冰冻+迷魂状态下，不可回春，但仍会攻击自己英雄
     */
    @Test
    public void test冰冻_迷魂_回春() {
        SkillTestContext context = prepare(50, 50, "水源制造者-1", "彩翼公主", "凤凰");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c凤凰 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0);   // 水源制造者冰冻
        random.addNextPicks(0).addNextNumbers(0);   // 彩翼公主迷魂
        context.proceedOneRound();

        context.proceedOneRound();

        Assert.assertEquals(
            435 /* 彩翼公主的攻击 */ + 660 /* 凤凰被迷魂的攻击 */,
            6390 - context.getPlayer(1).getHP());
        Assert.assertEquals(80 /* 水源冰弹 */ + 218 /* 水源攻击 */ - 0 /* 无法回春 */, 1560 - c凤凰.getHP());
    }

    @Test
    public void test燃烧_基础() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+燃烧10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(250, 1400 - c秘银巨石像.getHP());
    }

    /**
     * 燃烧比回春先结算
     */
    @Test
    public void test燃烧_回春() {
        SkillTestContext context = prepare(50, 50, "地狱红龙", "凤凰-5");
        context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(691);
        context.startGame();
        
        random.addNextPicks(0);     // 地狱红龙烈火焚神
        context.proceedOneRound();

        random.addNextPicks(0).addNextNumbers(0); // 凤凰烈焰风暴
        context.proceedOneRound();

        // 燃烧和回春结算前，凤凰HP还剩下: 691 - 540 - 150 = 1
        // 由于燃烧先结算，所以凤凰无法回春而死亡。
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
    }

    /**
     * 横扫的溅射部分无法引起燃烧
     */
    @Test
    public void test燃烧_横扫溅射() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+横扫", "占位符", "占位符+燃烧10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));
    }

    /**
     * 即使秒杀对手仍然会被燃烧
     */
    @Test
    public void test燃烧_死亡() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+燃烧10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));
    }

    /**
     * 同等级燃烧效果不能叠加
     */
    @Test
    public void test燃烧_同等级() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+燃烧10", "占位符+燃烧10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(2);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));
        context.proceedOneRound();
        context.proceedOneRound();
        Assert.assertEquals(1, c秘银巨石像.getStatus().getStatusOf(CardStatusType.燃烧).size());
    }

    /**
     * 法力反射无法防御燃烧
     */
    @Test
    public void test燃烧_法力反射() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+燃烧10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();

        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));
        Assert.assertEquals(250, 1400 - c秘银巨石像.getHP());
    }

    /**
     * 燃烧可以被免疫
     */
    @Test
    public void test燃烧_免疫() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+免疫", "占位符+燃烧10");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));
        Assert.assertEquals(0, 1550 - c秘银巨石像.getHP());
    }

    /**
     * 法力反射无法防御烈火焚神
     */
    @Test
    public void test烈火焚神_法力反射() {
        SkillTestContext context = prepare(50, 50, "占位符+烈火焚神10", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 烈火焚神10
        context.proceedOneRound();

        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());

        context.proceedOneRound();
        Assert.assertEquals(200, 1400 - c秘银巨石像.getHP());
    }

    @Test
    public void test烈火焚神_免疫() {
        SkillTestContext context = prepare(50, 50, "占位符+烈火焚神10", "秘银巨石像+免疫");
        context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 烈火焚神10
        context.proceedOneRound();
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.燃烧));

        context.proceedOneRound();
        Assert.assertEquals(0, 1550 - c秘银巨石像.getHP());
    }

    /**
     * 中毒比回春先结算
     */
    @Test
    public void test中毒_回春() {
        SkillTestContext context = prepare(50, 50, "蝎尾狮", "凤凰-5");
        context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(801);
        context.startGame();
        
        random.addNextPicks(0);     // 蝎尾狮毒液
        context.proceedOneRound();

        random.addNextPicks(0).addNextNumbers(0); // 凤凰烈焰风暴
        context.proceedOneRound();

        // 中毒和回春结算前，凤凰HP还剩下: 801 - 480 (攻击力) - 200 (背刺) - 120 (毒液) = 1
        // 由于中毒先结算，所以凤凰无法回春而死亡。
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
    }
    
    /**
     * 冰冻麻痹同时中的话，也会同时解除
     */
    @Test
    public void test冰冻_麻痹() {
        SkillTestContext context = prepare(50, 50, "水源制造者-1", "狮鹫-5", "凤凰");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c凤凰 = context.addToField(2, 1);
        context.startGame();
        
        random.addNextPicks(0).addNextNumbers(0);   // 水源制造者冰弹打凤凰，保证冰冻
        random.addNextPicks(0).addNextNumbers(0);   // 狮鹫连环闪电打凤凰，保证麻痹
        context.proceedOneRound();
        
        context.proceedOneRound();
        Assert.assertFalse("凤凰的麻痹应该已经解除", c凤凰.getStatus().containsStatus(CardStatusType.麻痹));
        Assert.assertFalse("凤凰的冰冻应该已经解除", c凤凰.getStatus().containsStatus(CardStatusType.冰冻));
    }

    /**
     * 基本的虚弱测试
     */
    @Test
    public void test虚弱() {
        SkillTestContext context = prepare(50, 50, "东方幻术师-1", "秘银巨石像");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 东方幻术师对秘银巨石像使用虚弱
        context.proceedOneRound();
        
        context.proceedOneRound();
        Assert.assertEquals(330, 1056 - c东方幻术师.getHP());
    }
    
    /**
     * 晕眩状态下被迷魂，仍然会攻击自己英雄
     */
    @Test
    public void test虚弱_晕眩_迷魂() {
        SkillTestContext context = prepare(50, 50, "东方幻术师-5", "怒雪咆哮-1", "占位符", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c怒雪咆哮 = context.addToField(1, 0);
        context.addToField(2, 1);
        CardInfo c秘银巨石像 = context.addToField(3, 1);
        context.startGame();

        // Enemy Round
        context.getStage().setActivePlayerNumber(1);
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(660, 1703 - c怒雪咆哮.getHP());

        // Player Round
        random.addNextPicks(1); // 东方幻术师对秘银巨石像使用虚弱
        random.addNextPicks(1).addNextNumbers(0); // 东方幻术师对秘银巨石像使用迷魂成功
        context.proceedOneRound();

        // Enemy Round
        int hp怒雪咆哮ThisRound = c怒雪咆哮.getHP();
        int hpP1ThisRound = context.getPlayer(1).getHP();
        context.proceedOneRound();
        Assert.assertEquals(hp怒雪咆哮ThisRound, c怒雪咆哮.getHP());    // 秘银巨石像被晕眩，无法攻击怒雪咆哮
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(330 /* 虚弱 */, hpP1ThisRound - context.getPlayer(1).getHP());
    }

    /**
     * 被迷魂且虚弱中的卡会以一半攻击力攻击自己英雄
     */
    @Test
    public void test虚弱_迷魂() {
        SkillTestContext context = prepare(50, 50, "东方幻术师-5", "秘银巨石像");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 东方幻术师对秘银巨石像使用虚弱
        random.addNextPicks(0).addNextNumbers(0); // 东方幻术师对秘银巨石像使用迷魂成功
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(0, 1160 - c东方幻术师.getHP());
        Assert.assertEquals(330, 6390 - context.getPlayer(1).getHP());
    }
    
    /**
     * 被虚弱后，暴击仍然以基础攻击力计算加成
     */
    @Test
    public void test虚弱_暴击() {
        SkillTestContext context = prepare(50, 50, "东方幻术师-1", "金属巨龙-5");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 东方幻术师对金属巨龙使用虚弱
        context.proceedOneRound();

        random.addNextNumbers(0); // 金属巨龙暴击
        context.proceedOneRound();
        Assert.assertEquals(505 * 220 / 100 /* 暴击 */ - 505 / 2 /* 虚弱 */, 1056 - c东方幻术师.getHP());
    }
    
    /**
     * 虚弱不可被免疫
     */
    @Test
    public void test虚弱_免疫() {
        SkillTestContext context = prepare(50, 50, "东方幻术师-1", "金属巨龙");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 东方幻术师对金属巨龙使用虚弱
        context.proceedOneRound();

        random.addNextNumbers(1000); // 金属巨龙未暴击
        context.proceedOneRound();
        Assert.assertEquals(655 / 2 + 1 /* 虚弱无效 */, 1056 - c东方幻术师.getHP());
    }

    @Test
    public void test虚弱_多重() {
        SkillTestContext context = prepare(50, 50, "东方幻术师-1*2", "秘银巨石像");
        CardInfo c东方幻术师1 = context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 东方幻术师1对秘银巨石像使用虚弱
        random.addNextPicks(0); // 东方幻术师2对秘银巨石像使用虚弱
        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(165, 1056 - c东方幻术师1.getHP());
        Assert.assertEquals(660, c秘银巨石像.getCurrentAT());
    }
    
    @Test
    public void test战争怒吼_虚弱() {
        SkillTestContext context = prepare(50, 50, "东方幻术师", "血色骑士", "秘银巨石像*2");
        CardInfo c东方幻术师 = context.addToField(0, 0);
        CardInfo c血色骑士 = context.addToHand(1, 0).setSummonDelay(0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        context.startGame();
        
        random.addNextPicks(0, 1); // 血色骑士的降临战争怒吼
        random.addNextPicks(0); // 东方幻术师虚弱秘银巨石像1
        random.addNextPicks(0).addNextNumbers(1000); // 东方幻术师迷魂失败
        random.addNextNumbers(1000); // 血色骑士暴击失败
        context.proceedOneRound();
        
        context.proceedOneRound();
        Assert.assertEquals(660 / 2 /* 虚弱 */ / 2 /* 战争怒吼 */, 1290 - c东方幻术师.getHP());
        Assert.assertEquals(660 / 2 /* 战争怒吼 */, 1210 - c血色骑士.getHP());
        Assert.assertEquals(660, c秘银巨石像1.getCurrentAT());
        Assert.assertEquals(660, c秘银巨石像2.getCurrentAT());
    }

    /**
     * 不屈状态下，连狙击都可以防住
     */
    @Test
    public void test不屈_狙击() {
        SkillTestContext context = prepare(50, 50, "残血王国小兵+不屈", "占位符", "秘银巨石像+狙击1");
        CardInfo c王国小兵 = context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertTrue(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c王国小兵.getHP());
        
        context.proceedOneRound();
        Assert.assertFalse(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertFalse(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(c王国小兵.isDead());
    }

    /**
     * 不屈无法消除DEBUFF
     */
    @Test
    public void test不屈_中毒() {
        SkillTestContext context = prepare(50, 50, "残血王国小兵+不屈", "占位符+毒云10");
        CardInfo c王国小兵 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextPicks(0);     // 占位符的毒云
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(c王国小兵.getStatus().containsStatus(CardStatusType.中毒));
        Assert.assertEquals(1, c王国小兵.getHP());

        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertFalse(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(c王国小兵.isDead());
    }

    @Test
    public void test不屈_法力反射() {
        SkillTestContext context = prepare(50, 50, "精灵法师+不屈", "秘银巨石像");
        CardInfo c精灵法师 = context.addToField(0, 0).setBasicHP(2);
        context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); // 火球
        random.addNextNumbers(0);
        random.addNextPicks(0); // 火墙
        random.addNextNumbers(0);
        context.proceedOneRound();
        Assert.assertEquals(1, c精灵法师.getHP());
        Assert.assertFalse(c精灵法师.getStatus().containsStatus(CardStatusType.不屈));

        context.proceedOneRound();
        Assert.assertTrue(c精灵法师.isDead());
    }

    /**
     * 根据官方BUG，不屈能触发死契技能，真正死的时候还能再触发一次
     */
    @Test
    public void test不屈_死契技能() {
        SkillTestContext context = prepare(50, 50, "铸造大师+死契暴风雪1", "占位符+狙击1");
        CardInfo c铸造大师 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextPicks(0).addNextNumbers(1000);    // 铸造大师发动死契暴风雪
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c铸造大师.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c铸造大师.getHP());
        Assert.assertEquals(20, 5000 - c占位符.getHP());

        context.proceedOneRound();

        random.addNextPicks(0).addNextNumbers(1000);    // 铸造大师真正死亡并再次发动死契暴风雪
        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertFalse(c铸造大师.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(c铸造大师.isDead());
        Assert.assertEquals(560 /* 普通攻击 */ + 20 + 20 /* 两次死契暴风雪 */, 5000 - c占位符.getHP());
    }
    
    /**
     * 在己方回合反击结算阶段发动的不屈，一直持续到那张卡下一回合的攻击阶段结束
     */
    @Test
    public void test不屈_盾刺() {
        SkillTestContext context = prepare(50, 50, "见习圣骑+不屈", "秘银巨石像+盾刺10");
        CardInfo c见习圣骑 = context.addToField(0, 0).setBasicHP(2);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c见习圣骑.getHP());

        // 本回合不屈依然有效
        random.addNextNumbers(1000); // 见习圣骑闪避失败
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c见习圣骑.getHP());

        // 本回合不屈失效，被盾刺弹死
        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertFalse(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(c见习圣骑.isDead());
    }

    
    /**
     * 在己方回合反击结算阶段发动的不屈，一直持续到那张卡下一回合的攻击阶段结束
     */
    @Test
    public void test不屈_连续盾刺() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "见习圣骑+不屈", "秘银巨石像+盾刺10*2");
        context.addToField(0, 0);
        CardInfo c见习圣骑 = context.addToField(1, 0).setBasicHP(2);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        // 本回合盾刺被发动两次，第一次触发不屈，第二次无法被不屈挡下，因为盾刺是在攻击阶段结算结束后发动的
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertFalse(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertTrue(c见习圣骑.isDead());
    }

    /**
     * 不屈能免疫瘟疫和群体削弱
     */
    @Test
    public void test不屈_瘟疫_群体削弱() {
        SkillTestContext context = prepare(
            50, 50, "见习圣骑+不屈", "秘银巨石像", "占位符+瘟疫10", "占位符+群体削弱10");
        CardInfo c见习圣骑 = context.addToField(0, 0).setBasicHP(2);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextNumbers(1000);   // 见习圣骑闪避失败
        random.addNextPicks(0, 0);     // 瘟疫和群体削弱
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c见习圣骑.getHP());
        Assert.assertEquals(495 - 50 /* 瘟疫 */ - 50 /* 群体削弱 */, c见习圣骑.getCurrentAT());
    }

    /**
     * 不屈能抵御控制技能
     */
    @Test
    public void test不屈_迷惑_冰冻_麻痹_锁定() {
        SkillTestContext context = prepare(
            50, 50, "见习圣骑+不屈", "秘银巨石像", "占位符+迷魂10", "占位符+冰弹10", "占位符+落雷10", "占位符+陷阱1");
        CardInfo c见习圣骑 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.addToField(4, 1);
        context.addToField(5, 1);
        context.startGame();

        context.getStage().setActivePlayerNumber(1);
        random.addNextNumbers(1000);    // 见习圣骑闪避失败
        random.addNextPicks(0, 0, 0, 0).addNextNumbers(0, 0, 0, 0);     // 迷魂冰弹落雷陷阱全中
        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c见习圣骑.getHP());

        context.proceedOneRound();
        // 见习圣骑并未受控制
        Assert.assertEquals(495, 1400 - c秘银巨石像.getHP());
    }

    /**
     * 发动不屈时不应该丢失种族之力
     */
    @Test
    public void test不屈_种族之力() {
        SkillTestContext context = prepare(50, 50, "铸造大师*2", "秘银巨石像");
        CardInfo c铸造大师1 = context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c铸造大师2 = context.addToHand(1, 0).setSummonDelay(0);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(460 + 150, c铸造大师1.getCurrentAT());
        Assert.assertEquals(460 + 150, c铸造大师2.getCurrentAT());

        c铸造大师1.setBasicHP(2);
        c铸造大师2.setBasicHP(2);
        context.proceedOneRound();
        Assert.assertTrue(c铸造大师1.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertFalse(c铸造大师2.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c铸造大师1.getHP());
        Assert.assertEquals(2, c铸造大师2.getHP());
        Assert.assertEquals(460 + 150, c铸造大师1.getCurrentAT());
        Assert.assertEquals(460 + 150, c铸造大师2.getCurrentAT());

        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(460, c铸造大师2.getCurrentAT());
    }

    /**
     * 不屈无法抵挡摧毁
     */
    @Test
    public void test不屈_摧毁() {
        SkillTestContext context = prepare(50, 50, "见习圣骑+不屈", "占位符+盾刺10", "独眼巨人");
        CardInfo c见习圣骑 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        c见习圣骑.setBasicHP(2);
        context.proceedOneRound();
        // 见习圣骑被雷兽盾刺弹死，发动不屈
        Assert.assertTrue(c见习圣骑.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c见习圣骑.getHP());

        context.addToHand(2, 1).setSummonDelay(0);
        random.addNextPicks(0);   // 独眼巨人的降临群体削弱
        random.addNextPicks(0);   // 独眼巨人的降临摧毁
        context.proceedOneRound();
        // 见习圣骑被摧毁，不屈无法抵挡摧毁
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
    }

    /**
     * 不屈无法抵挡送还
     */
    @Test
    public void test不屈_送还() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "残血王国小兵+不屈", "占位符+盾刺10", "占位符+送还");
        context.addToField(0, 0);
        CardInfo c王国小兵 = context.addToField(1, 0);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        c王国小兵.setBasicHP(2);
        context.proceedOneRound();
        // 王国小兵被雷兽盾刺弹死，发动不屈
        Assert.assertFalse(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c王国小兵.getHP());

        context.proceedOneRound();
        // 王国小兵被送还，不屈无法抵挡送还
        Assert.assertEquals(1, context.getPlayer(0).getField().size());
        Assert.assertEquals(1, context.getPlayer(0).getDeck().size());
    }

    /**
     * 不屈状态的卡不会被狙击
     */
    @Test
    public void test不屈_二重狙击() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "占位符+二重狙击10", "残血王国小兵+不屈", "占位符+盾刺10", "占位符+送还");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c王国小兵 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        CardInfo c占位符3 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(3, context.getPlayer(1).getField().size());
        Assert.assertEquals(1, c王国小兵.getHP());
        // 二重狙击打在两个后方占位符上
        Assert.assertEquals(250, 5000 - c占位符2.getHP());
        Assert.assertEquals(250, 5000 - c占位符3.getHP());
    }

    /**
     * 不屈状态的卡不会被治疗
     */
    @Test
    public void test不屈_治疗() {
        SkillTestContext context = prepare(
            50, 50, "占位符+治疗", "残血王国小兵+不屈", "秘银巨石像*2");
        context.addToField(0, 0);
        CardInfo c王国小兵 = context.addToField(1, 0).setBasicHP(2);
        context.addToField(2, 1);
        context.addToField(3, 1);

        context.startGame();
        context.getStage().setActivePlayerNumber(1);

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertTrue(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c王国小兵.getHP());

        random.addNextPicks(1);     // 占位符治疗王国小兵
        context.proceedOneRound();
        // 不屈的卡无法被治疗
        Assert.assertEquals(1, c王国小兵.getHP());
        Assert.assertFalse(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
    }

    /**
     * 不屈状态的卡不会被甘霖
     */
    @Test
    public void test不屈_甘霖() {
        SkillTestContext context = prepare(
                50, 50, "占位符+甘霖", "残血王国小兵+不屈", "秘银巨石像*2");
        context.addToField(0, 0);
        CardInfo c王国小兵 = context.addToField(1, 0).setBasicHP(2);
        context.addToField(2, 1);
        context.addToField(3, 1);

        context.startGame();
        context.getStage().setActivePlayerNumber(1);

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        Assert.assertTrue(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1, c王国小兵.getHP());

        random.addNextPicks(0, 1);     // 占位符甘霖
        context.proceedOneRound();
        // 不屈的卡无法被甘霖
        Assert.assertEquals(1, c王国小兵.getHP());
        Assert.assertFalse(c王国小兵.getStatus().containsStatus(CardStatusType.不屈));
    }

    /**
     * 不屈状态的卡可以吸血
     */
    @Test
    public void test不屈_吸血() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "熊人武士+不屈", "占位符+盾刺10", "占位符");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c熊人武士 = context.addToField(1, 0).setBasicHP(2);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(0).getField().size());
        // 不屈的卡可以吸血
        Assert.assertEquals(1 + 1170 /* 吸血 */, c熊人武士.getHP());
        // 二重狙击打在两个后方占位符上
        Assert.assertEquals(200 /* 盾刺 */, 1400 - c秘银巨石像.getHP());
    }

    /**
     * 不屈状态的卡会被虚弱
     */
    @Test
    public void test不屈_虚弱() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像", "占位符+虚弱", "秘银巨石像+不屈");
        CardInfo c秘银巨石像1 = context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像2 = context.addToField(2, 1).setBasicHP(2);
        context.startGame();

        random.addNextPicks(0); // 占位符虚弱对方秘银巨石像
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像2.getStatus().containsStatus(CardStatusType.不屈));
        // 不屈的卡仍然会被弱化
        Assert.assertTrue(c秘银巨石像2.getStatus().containsStatus(CardStatusType.弱化));

        context.proceedOneRound();
        Assert.assertEquals(810 / 2, 1400 - c秘银巨石像1.getHP());
    }

    /**
     * 不屈状态的卡也会被献祭
     */
    //@Test
    public void test不屈_献祭() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像*2", "铸造大师+死契复活", "秘银巨石像+献祭1");
        CardInfo c秘银巨石像1 = context.addToField(0, 0);
        context.addToField(1, 0).setBasicHP(2);
        CardInfo c铸造大师 = context.addToField(2, 1).setBasicHP(2);
        context.addToGrave(3, 1);
        context.startGame();

        random.addNextPicks(0); // 复活秘银巨石像
        random.addNextPicks(0); // 献祭铸造大师
        context.proceedOneRound();
        Assert.assertTrue(c铸造大师.isDead());

        context.proceedOneRound();
        Assert.assertEquals(1, context.getPlayer(1).getField().size());
        Assert.assertEquals(810 * 130 / 100, 1400 - c秘银巨石像1.getHP());
    }

    /**
     * 不屈无法触发自爆技能
     */
    @Test
    public void test不屈_自爆() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "铸造大师+自爆1");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c铸造大师 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();
        context.proceedOneRound();
        Assert.assertTrue(c铸造大师.getStatus().containsStatus(CardStatusType.不屈));
        Assert.assertEquals(1400, c秘银巨石像.getHP());
    }

    @Test
    public void test不屈_冰冻() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+冰弹1", "占位符+不屈");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 冰弹冰冻占位符
        context.proceedOneRound();
        Assert.assertTrue(c占位符.getStatus().containsStatus(CardStatusType.不屈));

        context.proceedOneRound();
        Assert.assertFalse(c占位符.getStatus().containsStatus(CardStatusType.不屈));
    }

    @Test
    public void test森林沐浴_基本() {
        SkillTestContext context = prepare(
            50, 50, "赤面天狗", "占位符");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();
        context.proceedOneRound();
        Assert.assertEquals(745 * 220 / 100 /* 污染7 */, 5000 - c占位符.getHP());
    }

    /**
     * 免疫脱困无法抵挡森林沐浴
     */
    @Test
    public void test森林沐浴_免疫_脱困() {
        SkillTestContext context = prepare(
            50, 50, "占位符+降临森林沐浴", "占位符+免疫", "占位符+脱困");
        context.addToHand(0, 0).setSummonDelay(0);
        CardInfo c占位符2 = context.addToField(1, 1);
        CardInfo c占位符3 = context.addToField(2, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(Race.FOREST, c占位符2.getRace());
        Assert.assertEquals(Race.FOREST, c占位符3.getRace());
    }

    /**
     * 森林沐浴的效果是在卡牌结束行动时解除的
     */
    @Test
    public void test森林沐浴_死契复活() {
        SkillTestContext context = prepare(
            50, 50, "占位符+降临森林沐浴", "占位符", "占位符+死契复活", "秘银巨石像*3");
        context.addToGrave(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0).setBasicHP(2);
        CardInfo c秘银巨石像1 = context.addToField(3, 1);
        CardInfo c秘银巨石像2 = context.addToField(4, 1);
        CardInfo c秘银巨石像3 = context.addToField(5, 1);
        context.startGame();
        context.getStage().setActivePlayerNumber(1);
        
        //这里的NextPick是指从墓地里拉出的卡的index，墓地里只有一张卡，所以应该是0
        random.addNextPicks(0); // 死契复活占位符+降临森林沐浴
        context.proceedOneRound();
        Assert.assertEquals(Race.FOREST, c秘银巨石像1.getRace());
        // 秘银巨石像2和3结束行动的同时解除沐浴状态
        Assert.assertEquals(Race.KINGDOM, c秘银巨石像2.getRace());
        Assert.assertEquals(Race.KINGDOM, c秘银巨石像3.getRace());
        
        context.proceedOneRound();
        context.proceedOneRound();
        Assert.assertEquals(Race.KINGDOM, c秘银巨石像1.getRace());
        Assert.assertEquals(Race.KINGDOM, c秘银巨石像2.getRace());
        Assert.assertEquals(Race.KINGDOM, c秘银巨石像3.getRace());
    }
    
    /**
     * 被森林沐浴改变成森林的卡无法享受森林之力的BUFF
     */
    @Test
    public void test森林沐浴_森林之力_主动() {
        SkillTestContext context = prepare(
            50, 50, "占位符+降临森林沐浴", "占位符+降临摧毁", "秘银巨石像", "金属巨龙", "森林女神+森林之力1");
        context.addToHand(0, 0).setSummonDelay(0);
        context.addToHand(1, 0).setSummonDelay(2);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        CardInfo c金属巨龙 = context.addToField(3, 1);
        context.addToHand(4, 1).setSummonDelay(0);
        context.startGame();
        
        context.proceedOneRound();

        context.proceedOneRound();
        // 秘银巨石像不能因为森林沐浴享受森林守护和森林之力
        Assert.assertEquals(660, c秘银巨石像.getCurrentAT());
        Assert.assertEquals(1400, c秘银巨石像.getHP());
        Assert.assertEquals(655 + 25 /* 森林之力1 */, c金属巨龙.getCurrentAT());
        Assert.assertEquals(1710 + 250 /* 森林之力1 */, c金属巨龙.getHP());
        
        random.addNextPicks(2); /* 摧毁森林女神 */
        context.proceedOneRound();
        Assert.assertEquals(660, c秘银巨石像.getCurrentAT());
        Assert.assertEquals(1400, c秘银巨石像.getHP());
        Assert.assertEquals(655, c金属巨龙.getCurrentAT());
        Assert.assertEquals(1710, c金属巨龙.getHP());
    }
    
    @Test
    public void test魔力印记_基本() {
        SkillTestContext context = prepare(
            50, 50, "占位符+火球8", "占位符+魔力法阵1", "占位符+火球4", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        CardInfo c占位符4 = context.addToField(3, 1);
        context.startGame();
        
        random.addNextPicks(0).addNextNumbers(0); /* 火球8 */
        random.addNextPicks(0); /* 魔力法阵 */
        random.addNextPicks(0).addNextNumbers(0); /* 火球4 */
        context.proceedOneRound();
        Assert.assertEquals(200 + 100 + 30 /* 只有第二个火球享受加成 */, 5000 - c占位符4.getHP());
        Assert.assertTrue(c占位符4.getStatus().containsStatus(CardStatusType.魔印));
    }
    
    @Test
    public void test魔力印记_多重() {
        SkillTestContext context = prepare(
            50, 50, "白羊座*2", "占位符*2");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c占位符1 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        context.startGame();
        random.addNextPicks(0, 1, 0, 1, 0, 1, 0, 1);
        random.addNextNumbers(0, 0, 0, 0);
        context.proceedOneRound();

        Assert.assertEquals(200 + 210 + 570 + 200 + 210 + 210, 5000 - c占位符1.getHP());
        Assert.assertEquals(200 + 210 + 200 + 210 + 210 + 570, 5000 - c占位符2.getHP());
    }
    
    @Test
    public void test魔力印记_免疫() {
        SkillTestContext context = prepare(
            50, 50, "占位符+魔力法阵1", "占位符+烈焰风暴4", "占位符+免疫", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c占位符3 = context.addToField(2, 1);
        CardInfo c占位符4 = context.addToField(3, 1);
        context.startGame();
        
        random.addNextPicks(0, 1); /* 魔力法阵 */
        random.addNextPicks(0, 1).addNextNumbers(0, 0); /* 烈焰风暴4 */
        context.proceedOneRound();
        Assert.assertEquals(0, 5000 - c占位符3.getHP());
        Assert.assertFalse(c占位符3.getStatus().containsStatus(CardStatusType.魔印));
        Assert.assertEquals(100 /* 烈焰风暴4 */ + 30 /* 魔力法阵加成 */, 5000 - c占位符4.getHP());
        Assert.assertTrue(c占位符4.getStatus().containsStatus(CardStatusType.魔印));
    }

    @Test
    public void test致盲_基本() {
        SkillTestContext context = prepare(
            50, 50, "占位符+致盲1", "秘银巨石像");
        CardInfo c占位符 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); /* 致盲 */
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));

        random.addNextNumbers(0); /* 被致盲的秘银巨石像被闪避 */
        context.proceedOneRound();
        Assert.assertEquals(0, 5000 - c占位符.getHP());
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
        
        random.addNextPicks(0); /*致盲 */
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
        
        random.addNextNumbers(1000); /* 被致盲的秘银巨石像破解闪避 */
        context.proceedOneRound();
        Assert.assertEquals(660, 5000 - c占位符.getHP());
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
    }
    
    @Test
    public void test致盲_闪避() {
        SkillTestContext context = prepare(
            50, 50, "星辰主宰+致盲1", "秘银巨石像");
        CardInfo c星辰主宰 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); /* 致盲 */
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));

        //random.addNextNumbers(0); /* 因为星辰主宰有闪避技能，必定闪避，所以不再需要随机数决定是否闪避成功 */
        context.proceedOneRound();
        Assert.assertEquals(0, 1730 - c星辰主宰.getHP());
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
    }
    
    @Test
    public void test致盲_免疫() {
        SkillTestContext context = prepare(
            50, 50, "占位符+致盲1", "秘银巨石像+免疫");
        CardInfo c占位符 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); /* 致盲 */
        context.proceedOneRound();
        // 致盲被免疫
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));

        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符.getHP());
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
    }
    
    @Test
    public void test致盲_脱困() {
        SkillTestContext context = prepare(
            50, 50, "占位符+致盲1", "秘银巨石像+脱困");
        CardInfo c占位符 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); /* 致盲 */
        context.proceedOneRound();
        // 脱困无法阻挡致盲
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));

        random.addNextNumbers(0); /* 被致盲的秘银巨石像被闪避 */
        context.proceedOneRound();
        Assert.assertEquals(0, 5000 - c占位符.getHP());
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
    }
    
    @Test
    public void test致盲_弱点攻击() {
        SkillTestContext context = prepare(
            50, 50, "占位符+致盲10", "秘银巨石像+弱点攻击");
        CardInfo c占位符 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();

        random.addNextPicks(0); /* 致盲 */
        context.proceedOneRound();
        Assert.assertTrue(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));

        random.addNextNumbers(0); /* 被致盲的秘银巨石像被闪避，但又会被弱点攻击破解 */
        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符.getHP());
        Assert.assertFalse(c秘银巨石像.getStatus().containsStatus(CardStatusType.致盲));
    }
    
    @Test
    public void test精神狂乱_普通() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像*3");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        CardInfo c秘银巨石像3 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(660, 1400 - c秘银巨石像1.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像2.getHP());
        Assert.assertEquals(660, 1400 - c秘银巨石像3.getHP());
    }
    
    @Test
    public void test精神狂乱_一边没卡() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像*3");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        CardInfo c秘银巨石像3 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(0); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(0, 1400 - c秘银巨石像1.getHP());
        Assert.assertEquals(660, 1400 - c秘银巨石像2.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像3.getHP());
    }

    @Test
    public void test精神狂乱_两边没卡() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());
    }

    /**
     * 精神狂乱可以被免疫
     */
    @Test
    public void test精神狂乱_免疫() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像", "金属巨龙", "秘银巨石像");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c金属巨龙 = context.addToField(3, 1);
        CardInfo c秘银巨石像2 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(0, 1400 - c秘银巨石像1.getHP());
        Assert.assertEquals(0, 1710 - c金属巨龙.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像2.getHP());
    }
    
    /**
     * 精神狂乱无法被脱困
     */
    @Test
    public void test精神狂乱_脱困() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像", "秘银巨石像+脱困", "秘银巨石像");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        CardInfo c秘银巨石像3 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(810, 1400 - c秘银巨石像1.getHP());
        Assert.assertEquals(0, 1550 - c秘银巨石像2.getHP());
        Assert.assertEquals(810, 1400 - c秘银巨石像3.getHP());
    }

    /*
     * 精神狂乱无法被格挡闪避冰甲
     */
    @Test
    public void test精神狂乱_格挡冰甲() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像+格挡10", "秘银巨石像", "秘银巨石像+冰甲10");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        CardInfo c秘银巨石像3 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(660, 1550 - c秘银巨石像1.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像2.getHP());
        Assert.assertEquals(660, 1550 - c秘银巨石像3.getHP());
    }

    /*
     * 精神狂乱无法被格挡闪避冰甲
     */
    @Test
    public void test精神狂乱_闪避() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "占位符+精神狂乱", "秘银巨石像", "秘银巨石像", "秘银巨石像+闪避10");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        CardInfo c秘银巨石像3 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(660, 1400 - c秘银巨石像1.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像2.getHP());
        Assert.assertEquals(660, 1550 - c秘银巨石像3.getHP());
    }

    /*
     * 被迷魂状态仍然可以发动精神狂乱
     */
    @Test
    public void test精神狂乱_迷魂() {
        SkillTestContext context = prepare(
            50, 50, "占位符+迷魂10", "占位符+精神狂乱", "秘银巨石像", "秘银巨石像", "秘银巨石像+闪避10");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        CardInfo c秘银巨石像3 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(1).addNextNumbers(0); /* 迷魂10 */
        random.addNextPicks(1); /* 精神狂乱 */
        context.proceedOneRound();
        Assert.assertEquals(660, 1400 - c秘银巨石像1.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像2.getHP());
        Assert.assertEquals(660, 1550 - c秘银巨石像3.getHP());
    }
    
    /**
     * 法力侵蚀攻击被石林保护的卡牌受到时也会造成三倍伤害
     */
    @Test
    public void test石林_法力侵蚀() {
        SkillTestContext context = prepare(
            50, 50, "占位符", "山羊人盾士*2", "占位符+法力侵蚀10", "石林");
        CardInfo c占位符1 = context.addToField(0, 0);
        context.addToGrave(1, 0);
        context.addToGrave(2, 0);
        CardInfo c占位符2 = context.addToField(3, 1);
        RuneInfo r石林 = context.addToRune(0, 0);
        context.startGame();

        context.proceedOneRound();

        random.addNextPicks(0);
        context.proceedOneRound();

        Assert.assertTrue(r石林.isActivated());
        Assert.assertEquals(200 * 3, 5000 - c占位符1.getHP());
        Assert.assertEquals(0, 5000 - c占位符2.getHP());
    }

    /**
     * 即使被锁定仍然可以释放净魂领域
     */
    @Test
    public void test净魂领域_陷阱() {
        SkillTestContext context = prepare(
            50, 50, "占位符+陷阱2", "占位符", "秘银巨石像+净魂领域", "秘银巨石像");
        CardInfo c占位符1 = context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 0);
        CardInfo c秘银巨石像1 = context.addToField(2, 1);
        CardInfo c秘银巨石像2 = context.addToField(3, 1);
        context.startGame();

        random.addNextPicks(0, 1);  // 陷阱2
        random.addNextNumbers(0, 0);// 陷阱2
        context.proceedOneRound();

        Assert.assertTrue(c秘银巨石像1.getStatus().containsStatus(CardStatusType.锁定));
        Assert.assertTrue(c秘银巨石像2.getStatus().containsStatus(CardStatusType.锁定));

        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符1.getHP());
        Assert.assertEquals(660, 5000 - c占位符2.getHP());
    }

    /**
     * 沉默是在攻击后解除的，回春仍然能发挥作用
     */
    @Test
    public void test沉默_回春() {
        SkillTestContext context = prepare(
            50, 50, "秘银巨石像+沉默", "占位符+回春1");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.getStatus().containsStatus(CardStatusType.沉默));
        Assert.assertEquals(810, 5000 - c占位符.getHP());

        context.proceedOneRound();
        Assert.assertFalse(c占位符.getStatus().containsStatus(CardStatusType.沉默));
        Assert.assertEquals(810 - 30, 5000 - c占位符.getHP());
    }
    
    @Test
    public void test全体裂伤_基础() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+全体裂伤", "秘银巨石像-15", "占位符+回春1*2", "占位符+甘霖10");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c占位符1 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        CardInfo c占位符3 = context.addToField(4, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符1.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(810, 5000 - c占位符1.getHP());
        Assert.assertTrue(c占位符2.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(810, 5000 - c占位符2.getHP());
        Assert.assertTrue(c占位符3.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(0, 5000 - c占位符3.getHP());

        context.proceedOneRound();
        Assert.assertTrue(c占位符1.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(810, 5000 - c占位符1.getHP());
        Assert.assertTrue(c占位符2.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(810, 5000 - c占位符2.getHP());
        Assert.assertTrue(c占位符3.getStatus().containsStatus(CardStatusType.裂伤));
        Assert.assertEquals(0, 5000 - c占位符3.getHP());
    }
}
