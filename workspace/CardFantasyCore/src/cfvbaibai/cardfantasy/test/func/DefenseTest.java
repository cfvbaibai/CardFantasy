package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class DefenseTest extends SkillValidationTest {
    /**
     * 多重冰甲效果只取最强力的那个
     */
    @Test
    public void test冰封_冰甲() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "金属巨龙-1", "战斗猛犸象", "冰封");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象 = context.addToField(1, 1);
        RuneInfo r冰封 = context.addToRune(0, 1);
        context.startGame();

        r冰封.activate();
        random.addNextNumbers(1000); // 金属巨龙不暴击
        context.proceedOneRound();

        Assert.assertEquals(100 /* 猛犸象的冰甲7被冰封的冰甲9覆盖 */, 1220 - c战斗猛犸象.getHP());
    }
    
    /**
     * 格挡先于冰封结算
     */
    @Test
    public void test格挡_冰封() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像", "金属巨龙", "冰封");
        context.addToField(0, 0);
        CardInfo c金属巨龙 = context.addToField(1, 1);
        RuneInfo r冰封 = context.addToRune(0, 1);
        context.startGame();

        r冰封.activate();
        context.proceedOneRound();

        Assert.assertEquals(100 /* 金属巨龙的格挡8先结算，再结算冰封的冰甲9 */, 1710 - c金属巨龙.getHP());
    }

    /**
     * 岩壁后于冰甲结算
     */
    @Test
    public void test岩壁_冰甲() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像", "凤凰+冰甲1", "岩壁-1");
        context.addToField(0, 0);
        CardInfo c冰甲凤凰 = context.addToField(1, 1);
        RuneInfo r岩壁 = context.addToRune(0, 1);
        context.startGame();

        r岩壁.activate();
        context.proceedOneRound();

        Assert.assertEquals(180 - 120 /* 猛犸的冰甲先结算，然后结算岩壁 */, 1690 - c冰甲凤凰.getHP());
    }
    
    /**
     * 横扫为溅射
     */
    @Test
    public void test横扫_格挡() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "牛头人酋长*2");
        context.addToField(0, 0);
        CardInfo c牛头人酋长1 = context.addToField(1, 1);
        CardInfo c牛头人酋长2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(630 - 140 /* 格挡 */, 1050 - c牛头人酋长1.getHP());
        Assert.assertEquals((630 - 140) /* 先算溅射伤害 */ - 140 /* 2号位酋长的格挡 */, 1050 - c牛头人酋长2.getHP());
    }
    
    /**
     * 横扫为溅射
     */
    @Test
    public void test横扫_冰甲() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "战斗猛犸象", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象 = context.addToField(1, 1);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(120 /* 冰甲 */, 1220 - c战斗猛犸象.getHP());
        Assert.assertEquals(120 /* 溅射 */, 1400 - c秘银巨石像.getHP());
    }

    /**
     * 正面被闪避的话就无法溅射
     */
    @Test
    public void test横扫_正面闪避() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "大剑圣", "秘银巨石像");
        context.addToField(0, 0);
        CardInfo c大剑圣 = context.addToField(1, 1);
        CardInfo c秘银巨石像 = context.addToField(2, 1);
        context.startGame();

        random.addNextNumbers(0); // 大剑圣闪避成功
        context.proceedOneRound();

        Assert.assertEquals(0, 995 - c大剑圣.getHP());
        Assert.assertEquals(0, 1400 - c秘银巨石像.getHP());
    }
    
    /**
     * 侧翼被闪避的话，仍能继续溅射另一侧
     */
    @Test
    public void test横扫_侧翼闪避() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符", "光明之龙", "大剑圣", "秘银巨石像*2");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c大剑圣 = context.addToField(2, 1);
        context.addToField(3, 1);
        CardInfo c秘银巨石像2 = context.addToField(4, 1);
        context.startGame();

        random.addNextNumbers(0); // 大剑圣闪避占位符攻击成功
        random.addNextNumbers(0); // 大剑圣闪避光明之龙攻击成功
        context.proceedOneRound();

        Assert.assertEquals(0, 995 - c大剑圣.getHP());
        Assert.assertEquals(630, 1400 - c秘银巨石像2.getHP());
    }

    /**
     * 连锁攻击能够被格挡, 且攻击力按照被格挡之后的算
     */
    @Test
    public void test连锁攻击_格挡() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "麒麟兽", "牛头人酋长*3");
        context.addToField(0, 0);
        CardInfo c牛头人酋长1 = context.addToField(1, 1);
        CardInfo c牛头人酋长2 = context.addToField(2, 1);
        CardInfo c牛头人酋长3 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(610 - 140 /* 格挡 */, 1050 - c牛头人酋长1.getHP());
        Assert.assertEquals((610 - 140) * 175 / 100 - 140 /* 格挡 */, 1050 - c牛头人酋长2.getHP());
        Assert.assertEquals((610 - 140) * 175 / 100 - 140 /* 格挡 */, 1050 - c牛头人酋长3.getHP());
    }
    
    /**
     * 连锁攻击能够被冰甲阻挡
     */
    @Test
    public void test连锁攻击_冰甲() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "麒麟兽", "战斗猛犸象*2");
        context.addToField(0, 0);
        CardInfo c战斗猛犸象1 = context.addToField(1, 1);
        CardInfo c战斗猛犸象2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(120 /* 冰甲 */, 1220 - c战斗猛犸象1.getHP());
        Assert.assertEquals(120 /* 冰甲 */, 1220 - c战斗猛犸象2.getHP());
    }
    
    /**
     * 连锁攻击被正面闪避的话就无法触发
     */
    @Test
    public void test连锁攻击_正面闪避() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "麒麟兽", "大剑圣*2");
        context.addToField(0, 0);
        CardInfo c大剑圣1 = context.addToField(1, 1);
        CardInfo c大剑圣2 = context.addToField(2, 1);
        context.startGame();

        random.addNextNumbers(0); // 大剑圣1闪避麒麟兽攻击成功
        random.addNextNumbers(1000); // 假设大剑圣2闪避麒麟兽攻击失败
        context.proceedOneRound();
        Assert.assertEquals(0, 995 - c大剑圣1.getHP());
        Assert.assertEquals(0, 995 - c大剑圣2.getHP());
    }
    
    /**
     * 连锁攻击被正面闪避的话就无法触发
     */
    @Test
    public void test连锁攻击_连锁闪避() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "麒麟兽", "大剑圣*4");
        context.addToField(0, 0);
        CardInfo c大剑圣1 = context.addToField(1, 1);
        CardInfo c大剑圣2 = context.addToField(2, 1);
        CardInfo c大剑圣3 = context.addToField(3, 1);
        CardInfo c大剑圣4 = context.addToField(4, 1);
        context.startGame();

        random.addNextNumbers(1000); // 大剑圣1闪避麒麟兽攻击失败
        random.addNextNumbers(1000); // 大剑圣2闪避麒麟兽攻击失败
        random.addNextNumbers(0); // 大剑圣3闪避麒麟兽攻击成功
        random.addNextNumbers(1000); // 假设大剑圣4闪避麒麟兽攻击失败
        context.proceedOneRound();
        Assert.assertEquals(610, 995 - c大剑圣1.getHP());
        Assert.assertEquals(995, 995 - c大剑圣2.getHP());
        Assert.assertEquals(0, 995 - c大剑圣3.getHP());
        Assert.assertEquals(0, 995 - c大剑圣4.getHP());
    }

    @Test
    public void test圣盾_基本() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像", "混元大师");
        context.addToField(0, 0);
        CardInfo c混元大师 = context.addToHand(1, 1).setSummonDelay(0);
        context.startGame();

        context.proceedOneRound();

        context.proceedOneRound();

        context.proceedOneRound();
        Assert.assertEquals(0, 1690 - c混元大师.getHP());  // 被圣盾完全防御

        context.proceedOneRound();
        context.proceedOneRound();
        Assert.assertEquals(660, 1690 - c混元大师.getHP()); // 圣盾只能用一次
    }

    /**
     * 圣盾转生后又可用
     */
    @Test
    public void test圣盾_转生() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "秘银巨石像-15", "混元大师+转生10");
        context.addToField(0, 0);
        CardInfo c混元大师 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, 1560 - c混元大师.getHP());  // 被圣盾完全防御

        context.getStage().setActivePlayerNumber(0);    // 强制混元大师继续挨打

        context.proceedOneRound();
        Assert.assertEquals(810, 1560 - c混元大师.getHP()); // 圣盾只能用一次
        
        context.getStage().setActivePlayerNumber(0);    // 强制混元大师继续挨打
        random.addNextNumbers(0);   // 混元大师转生成功
        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(1).getField().size()); // 混元大师应该被打死了

        c混元大师.setSummonDelay(0);
        context.proceedOneRound();
        Assert.assertEquals(1560 + 300 /* 神圣守护 */, c混元大师.getHP()); 
        
        context.proceedOneRound();
        Assert.assertEquals(0, 1560 + 300 - c混元大师.getHP());  // 被圣盾再次完全防御
        
        context.getStage().setActivePlayerNumber(0);    // 强制混元大师继续挨打

        context.proceedOneRound();
        Assert.assertEquals(810, 1560 + 300 - c混元大师.getHP()); // 转生后圣盾也只能用一次
    }
    
    @Test
    public void test圣盾_横扫_溅射破盾() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "秘银巨石像", "占位符", "混元大师-15");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 1);
        CardInfo c混元大师 = context.addToField(3, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(660, 1560 - c混元大师.getHP()); /* 光明之龙的横扫被圣盾抵挡破盾，秘银巨石像的攻击有效 */
    }
    
    @Test
    public void test圣盾_横扫_完全抵挡() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "混元大师-15", "占位符");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, 5000 - c占位符.getHP()); /* 光明之龙的横扫被正面圣盾抵挡，无法溅射 */
    }
    
    /**
     * 大地之盾不能被免疫
     */
    @Test
    public void test大地之盾_免疫() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "金属巨龙", "怒雪咆哮-1");
        CardInfo c金属巨龙 = context.addToField(0, 0);
        CardInfo c怒雪咆哮 = context.addToField(1, 1);
        context.startGame();
        
        random.addNextNumbers(1000);    // 金属巨龙不暴击
        context.proceedOneRound();
        Assert.assertTrue(c金属巨龙.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(655, 1703 - c怒雪咆哮.getHP());

        context.proceedOneRound();

        context.proceedOneRound(); // 金属巨龙因为晕眩无法行动
        Assert.assertFalse(c金属巨龙.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(655, 1703 - c怒雪咆哮.getHP());
    }

    /**
     * 大地之盾能被脱困
     */
    @Test
    public void test大地之盾_脱困() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "皇室舞者-1", "怒雪咆哮-1");
        CardInfo c皇室舞者 = context.addToField(0, 0);
        CardInfo c怒雪咆哮 = context.addToField(1, 1);
        context.startGame();
        
        random.addNextNumbers(1000);    // 金属巨龙不暴击
        context.proceedOneRound();
        Assert.assertFalse(c皇室舞者.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(234, 1703 - c怒雪咆哮.getHP());
    }

    /**
     * 溅射也会造成晕眩
     */
    @Test
    public void test大地之盾_横扫() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "占位符", "怒雪咆哮-1");
        CardInfo c光明之龙 = context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c怒雪咆哮 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c光明之龙.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(630, 1703 - c怒雪咆哮.getHP());

        random.addNextNumbers(0);       // 光明之龙闪避成功
        context.proceedOneRound();

        context.proceedOneRound(); // 光明之龙因为晕眩无法行动
        Assert.assertFalse(c光明之龙.getStatus().containsStatus(CardStatusType.晕眩));
        Assert.assertEquals(630, 1703 - c怒雪咆哮.getHP());
    }
    
    /**
     * 多重大地之盾也是一起解除
     */
    @Test
    public void test大地之盾_多重() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "光明之龙", "怒雪咆哮-1*2");
        CardInfo c光明之龙 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c光明之龙.getStatus().containsStatus(CardStatusType.晕眩));

        random.addNextNumbers(0);       // 光明之龙闪避成功
        context.proceedOneRound();

        context.proceedOneRound(); // 光明之龙因为晕眩无法行动
        Assert.assertFalse(c光明之龙.getStatus().containsStatus(CardStatusType.晕眩));
    }

    /**
     * 零伤害不会触发大地之盾
     */
    @Test
    public void test大地之盾_零伤害() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符", "占位符+大地之盾");
        CardInfo c占位符1 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(5000, c占位符1.getHP());
        Assert.assertFalse(c占位符1.getStatus().containsStatus(CardStatusType.晕眩));
    }

    /**
     * 闪避不会触发大地之盾
     */
    @Test
    public void test大地之盾_闪避() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符", "堕落精灵+大地之盾");
        CardInfo c占位符1 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();
        
        random.addNextNumbers(0);   // 堕落精灵闪避成功
        context.proceedOneRound();
        Assert.assertEquals(5000, c占位符1.getHP());
        Assert.assertFalse(c占位符1.getStatus().containsStatus(CardStatusType.晕眩));
    }

    /**
     * 种族之盾总是按照伤害，而不是对方的攻击力算
     */
    @Test
    public void test种族之盾_嗜血() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "战斗猛犸象", "半鹿人号角手");
        CardInfo c战斗猛犸象 = context.addToField(0, 0);
        CardInfo c半鹿人号角手 = context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(495 * 60 / 100, 1270 - c半鹿人号角手.getHP());
        
        context.proceedOneRound();
        Assert.assertEquals(545, c战斗猛犸象.getCurrentAT());
        
        context.proceedOneRound();
        Assert.assertEquals(495 * 60 / 100 + 545 * 60 / 100, 1270 - c半鹿人号角手.getHP());
    }

    /**
     * 种族之盾和格挡是按照卡面技能顺序发动的
     */
    @Test
    public void test种族之盾_格挡() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "战斗猛犸象*2", "半鹿人号角手+格挡10", "金属巨龙+森林之盾5");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c半鹿人号角手 = context.addToField(2, 1);
        CardInfo c金属巨龙 = context.addToField(3, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(495 * 60 / 100 /* 森林之盾5 */ - 200 /* 格挡10 */, 1415 - c半鹿人号角手.getHP());
        Assert.assertEquals((495 - 160 /* 格挡8 */) * 60 / 100 /* 森林之盾5 */, 1825 - c金属巨龙.getHP());
    }

    /**
     * 横扫的溅射伤害按照被格挡后的伤害算
     */
    @Test
    public void test种族之盾_横扫() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "震源岩蟾", "半鹿人号角手", "占位符");
        context.addToField(0, 0);
        CardInfo c半鹿人号角手 = context.addToField(1, 1);
        CardInfo c占位符 = context.addToField(2, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(620 * 60 / 100, 1270 - c半鹿人号角手.getHP());
        Assert.assertEquals(620 * 60 / 100, 5000 - c占位符.getHP());
    }
    
    @Test
    public void test反击_灵巧() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "圣泉元神", "占位符+反击10");
        CardInfo c圣泉元神 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(0, 1900 - c圣泉元神.getHP());
    }
    
    @Test
    public void test盾刺_灵巧() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "圣泉元神", "秘银巨石像", "占位符+盾刺10", "占位符+盾刺10");
        CardInfo c圣泉元神 = context.addToField(0, 0);
        CardInfo c秘银巨石像 = context.addToField(1, 0);
        context.addToField(2, 1);
        context.addToField(3, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(0, 1900 - c圣泉元神.getHP());
        Assert.assertEquals(400 /* 圣泉横扫被盾刺 */ + 200 /* 秘银自己攻击被盾刺 */, 1400 - c秘银巨石像.getHP());
    }
    
    /**
     * 如果鲜血盛宴被反弹怎无法加血，被弹死的话就死了
     */
    @Test
    public void test鲜血盛宴_法力反射() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符+鲜血盛宴1", "秘银巨石像");
        CardInfo c占位符 = context.addToField(0, 0).setBasicHP(500);
        CardInfo c秘银巨石像 = context.addToField(1, 1);
        context.startGame();
        
        random.addNextPicks(0);
        context.proceedOneRound();
        Assert.assertEquals(1400, c秘银巨石像.getHP());
        Assert.assertEquals(500 - 180 /* 法力反射6 */, c占位符.getHP());
    }
    
    /**
     * 鲜血盛宴可能发动到一半被弹死，这时候剩余的对象不发动
     */
    @Test
    public void test鲜血盛宴_法力反射_多对象() {
        SkillTestContext context = SkillValidationTestSuite.prepare(50, 50, "占位符+鲜血盛宴1", "秘银巨石像*2", "占位符");
        CardInfo c占位符1 = context.addToField(0, 0).setBasicHP(200);
        CardInfo c秘银巨石像1 = context.addToField(1, 1);
        CardInfo c秘银巨石像2 = context.addToField(2, 1);
        CardInfo c占位符2 = context.addToField(3, 1);
        context.startGame();
        
        random.addNextPicks(0, 1, 2);
        context.proceedOneRound();
        Assert.assertEquals(1400, c秘银巨石像1.getHP());
        Assert.assertEquals(1400, c秘银巨石像2.getHP());
        // 占位符1已经被第二个秘银巨石像弹死，无法继续发动鲜血盛宴到占位符2
        Assert.assertEquals(5000, c占位符2.getHP());
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertTrue(c占位符1.isDead());
    }
    
    @Test
    public void test守护_残血() throws HeroDieSignal {
        SkillTestContext context = SkillValidationTestSuite.prepare(
            50, 50, "占位符", "秘银巨石像", "占位符+守护");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c守护占位符 = context.addToField(2, 1);
        context.getPlayer(1).setHP(10);
        context.startGame();
        context.proceedOneRound();
        Assert.assertEquals(10, 5000 - c守护占位符.getHP());
    }
}
