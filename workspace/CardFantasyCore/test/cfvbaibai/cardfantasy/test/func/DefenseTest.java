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
        SkillTestContext context = prepare(50, 50, "金属巨龙-1", "战斗猛犸象", "冰封");
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
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "金属巨龙", "冰封");
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
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "凤凰+冰甲1", "岩壁-1");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "牛头人酋长*2");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "战斗猛犸象", "秘银巨石像");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "大剑圣", "秘银巨石像");
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
        SkillTestContext context = prepare(50, 50, "占位符", "光明之龙", "大剑圣", "秘银巨石像*2");
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
        SkillTestContext context = prepare(50, 50, "麒麟兽", "牛头人酋长*3");
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
        SkillTestContext context = prepare(50, 50, "麒麟兽", "战斗猛犸象*2");
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
        SkillTestContext context = prepare(50, 50, "麒麟兽", "大剑圣*2");
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
        SkillTestContext context = prepare(50, 50, "麒麟兽", "大剑圣*4");
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
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "混元大师");
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
        SkillTestContext context = prepare(50, 50, "秘银巨石像-15", "混元大师+转生10");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "秘银巨石像", "占位符", "混元大师-15");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "混元大师-15", "占位符");
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
        SkillTestContext context = prepare(50, 50, "金属巨龙", "怒雪咆哮-1");
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
        SkillTestContext context = prepare(50, 50, "皇室舞者-1", "怒雪咆哮-1");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "占位符", "怒雪咆哮-1");
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
        SkillTestContext context = prepare(50, 50, "光明之龙", "怒雪咆哮-1*2");
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
        SkillTestContext context = prepare(50, 50, "占位符", "占位符+大地之盾");
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
        SkillTestContext context = prepare(50, 50, "占位符", "堕落精灵+大地之盾");
        CardInfo c占位符1 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();
        
        random.addNextNumbers(0);   // 堕落精灵闪避成功
        context.proceedOneRound();
        Assert.assertEquals(5000, c占位符1.getHP());
        Assert.assertFalse(c占位符1.getStatus().containsStatus(CardStatusType.晕眩));
    }

    @Test
    public void test种族之盾_基本() {
        SkillTestContext context = prepare(50, 50, "金属巨龙", "恶灵之剑");
        context.addToField(0, 0);
        CardInfo c恶灵之剑 = context.addToField(1, 1);
        context.startGame();

        random.addNextNumbers(1000); // 金属巨龙暴击失败
        context.proceedOneRound();
        Assert.assertEquals(328 /* 被地狱之盾挡住50%伤害 */, 1350 - c恶灵之剑.getHP());
    }

    /**
     * 种族之盾总是按照伤害，而不是对方的攻击力算
     */
    @Test
    public void test种族之盾_嗜血() {
        SkillTestContext context = prepare(50, 50, "战斗猛犸象", "半鹿人号角手");
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
        SkillTestContext context = prepare(50, 50, "战斗猛犸象*2", "半鹿人号角手+格挡10", "金属巨龙+森林之盾5");
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
        SkillTestContext context = prepare(50, 50, "震源岩蟾", "半鹿人号角手", "占位符");
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
        SkillTestContext context = prepare(50, 50, "圣泉元神", "占位符+反击10");
        CardInfo c圣泉元神 = context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();
        
        context.proceedOneRound();
        Assert.assertEquals(0, 1900 - c圣泉元神.getHP());
    }
    
    @Test
    public void test盾刺_灵巧() {
        SkillTestContext context = prepare(50, 50, "圣泉元神", "秘银巨石像", "占位符+盾刺10", "占位符+盾刺10");
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
        SkillTestContext context = prepare(50, 50, "占位符+鲜血盛宴1", "秘银巨石像");
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
        SkillTestContext context = prepare(50, 50, "占位符+鲜血盛宴1", "秘银巨石像*2", "占位符");
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
        SkillTestContext context = prepare(
            50, 50, "占位符", "秘银巨石像", "占位符+守护");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c守护占位符 = context.addToField(2, 1);
        context.getPlayer(1).setHP(10);
        context.startGame();
        context.proceedOneRound();
        Assert.assertEquals(10, 5000 - c守护占位符.getHP());
    }
    
    @Test
    public void test水流护甲_基本() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+水流护甲1");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.getPlayer(1).setHP(100);
        context.startGame();
        context.proceedOneRound();
        Assert.assertEquals(600, 5000 - c占位符.getHP());
        Assert.assertEquals(100 + 50, context.getPlayer(1).getHP());
    }

    /*
     * 弱点攻击无法破解水流护甲
     */
    @Test
    public void test水流护甲_弱点攻击() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+弱点攻击", "占位符+水流护甲1");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.getPlayer(1).setHP(100);
        context.startGame();
        context.proceedOneRound();
        Assert.assertEquals(600, 5000 - c占位符.getHP());
        Assert.assertEquals(100 + 50, context.getPlayer(1).getHP());
    }

    /*
     * 水流护甲无法防御燕返
     */
    @Test
    public void test水流护甲_燕返() throws HeroDieSignal {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+水流护甲1", "魔剑士+燕返");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c魔剑士 = context.addToField(1, 1);
        c魔剑士.setBasicHP(1);
        context.getPlayer(1).setHP(100);
        context.startGame();
        context.proceedOneRound();
        Assert.assertTrue(c魔剑士.isDead());
        Assert.assertEquals(100 /* 水流护甲面对燕返无效 */, context.getPlayer(1).getHP());
        Assert.assertEquals(275 * 2, 1550 - c秘银巨石像.getHP());
    }
    
    /*
     * 水流护甲和格挡是相同发动时机，结算顺序按技能顺序来
     */
    @Test
    public void test水流护甲_格挡() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "叹惋之歌+格挡5");
        context.addToField(0, 0);
        CardInfo c叹惋之歌 = context.addToField(1, 1);
        context.getPlayer(1).setHP(100);
        context.startGame();
        context.proceedOneRound();

        Assert.assertEquals(100 + 300 /* 水流护甲6，格挡掉的部分不会影响水流护甲的回复量 */, context.getPlayer(1).getHP());
        Assert.assertEquals(350 /* 水流护甲6 */ - 100 /* 格挡5 */, 2205 - c叹惋之歌.getHP());
    }

    /*
     * 即使英雄在回合中被杀死，仍能在本回合被水流护甲救回
     */
    @Test
    public void test水流护甲_复活英雄() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+魔神之咒10", "占位符+水流护甲10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.getPlayer(1).setHP(100);
        context.startGame();
        context.proceedOneRound();

        random.addNextPicks(0);
        Assert.assertEquals(500, context.getPlayer(1).getHP());
        Assert.assertEquals(150, 5000 - c占位符.getHP());
    }

    @Test
    public void test水流护甲_未超额() {
        SkillTestContext context = prepare(50, 50, "魔剑士", "占位符+水流护甲10");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.getPlayer(1).setHP(100);
        context.startGame();
        context.proceedOneRound();
        Assert.assertEquals(150, 5000 - c占位符.getHP());
        Assert.assertEquals(100 + (225 - 150), context.getPlayer(1).getHP());
    }

    @Test
    public void test骑士守护_基本() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+骑士守护");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(660 / 2 /* 骑士守护减半伤害 */, 5000 - c占位符.getHP());
    }
    
    /**
     * 骑士守护无法防御斩杀
     */
    @Test
    public void test骑士守护_斩杀() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+斩杀", "占位符+骑士守护");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1);
        c占位符.setBasicHP(1000);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c占位符.isDead());
    }
    
    /**
     * 骑士守护能挡住横扫
     */
    @Test
    public void test骑士守护_横扫() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+横扫", "占位符", "占位符+骑士守护");
        context.addToField(0, 0);
        CardInfo c占位符1 = context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(810, 5000 - c占位符1.getHP());
        Assert.assertEquals(810 / 2 /* 骑士守护减半伤害 */, 5000 - c占位符2.getHP());
    }

    @Test
    public void test骑士守护_魔法() {
        SkillTestContext context = prepare(50, 50, "占位符+火球2", "占位符+冰弹2", "占位符+落雷2", "占位符+血炼2", "占位符+骑士守护");
        context.addToField(0, 0);
        context.addToField(1, 0);
        context.addToField(2, 0);
        context.addToField(3, 0);
        CardInfo c占位符 = context.addToField(4, 1);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 火球
        random.addNextPicks(0).addNextNumbers(0); // 冰弹
        random.addNextPicks(0).addNextNumbers(0); // 落雷
        random.addNextPicks(0); // 血炼

        context.proceedOneRound();
        int damage = 25 /* 火球2减半 */ + 20 /* 冰弹2减半 */ + 25 /* 落雷2减半 */ + 20 /* 血炼2减半 */;
        Assert.assertEquals(damage, 5000 - c占位符.getHP());
    }

    @Test
    public void test骑士守护_魔法_鲜血盛宴() {
        SkillTestContext context = prepare(50, 50, "占位符+鲜血盛宴10", "占位符+骑士守护*2");
        context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 1);
        CardInfo c占位符3 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0, 1); // 血炼

        context.proceedOneRound();
        int damage = 200 / 2 /* 鲜血盛宴被挡住一半伤害 */;
        Assert.assertEquals(damage, 5000 - c占位符2.getHP());
        Assert.assertEquals(damage, 5000 - c占位符3.getHP());
    }

    @Test
    public void test骑士守护_魔法_烈焰风暴() {
        SkillTestContext context = prepare(50, 50, "占位符+烈焰风暴10", "占位符+骑士守护*2");
        context.addToField(0, 0);
        CardInfo c占位符2 = context.addToField(1, 1);
        CardInfo c占位符3 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0, 1).addNextNumbers(0, 0);   // 烈焰风暴10
        context.proceedOneRound();
        int damage = 250 / 2 /* 烈焰风暴被挡住一半伤害 */;
        Assert.assertEquals(damage, 5000 - c占位符2.getHP());
        Assert.assertEquals(damage, 5000 - c占位符3.getHP());
    }

    /**
     * 骑士守护无法防御精神狂乱
     */
    @Test
    public void test骑士守护_精神狂乱() {
        SkillTestContext context = prepare(50, 50, "占位符+精神狂乱", "秘银巨石像", "占位符+骑士守护");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0); // 精神狂乱
        context.proceedOneRound();
        Assert.assertEquals(660, 5000 - c占位符2.getHP());
    }
    
    /**
     * 骑士守护无法减少燕返伤害
     */
    @Test
    public void test骑士守护_燕返() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+骑士守护", "魔剑士+燕返");
        CardInfo c秘银巨石像 = context.addToField(0, 0);
        CardInfo c魔剑士 = context.addToField(1, 1);
        c魔剑士.setBasicHP(2);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(275 * 2 /* 燕返伤害无法被减免 */, 1550 - c秘银巨石像.getHP());
    }
    
    @Test
    public void test骑士守护_物理攻击_致死() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "残血王国小兵+骑士守护");
        context.addToField(0, 0);
        CardInfo c残血王国小兵 = context.addToField(1, 1).setBasicHP(10);
        context.startGame();

        context.proceedOneRound();
        Assert.assertTrue(c残血王国小兵.isDead()); // 骑士守护无法防御物理致死攻击
    }

    @Test
    public void test骑士守护_魔法攻击_致死() {
        SkillTestContext context = prepare(50, 50, "占位符+血炼10*2", "残血王国小兵+骑士守护");
        context.addToField(0, 0);
        context.addToField(1, 0);
        CardInfo c残血王国小兵 = context.addToField(2, 1).setBasicHP(7);
        context.startGame();

        random.addNextPicks(0, 0); // 血炼10
        context.proceedOneRound();
        Assert.assertEquals(1 /* 法术致死伤害按剩余血量一半算 */, c残血王国小兵.getHP());
        Assert.assertFalse(c残血王国小兵.isDead());
    }

    @Test
    public void test骑士守护_魔法攻击_杀死() {
        SkillTestContext context = prepare(50, 50, "占位符+血炼10", "残血王国小兵+骑士守护");
        context.addToField(0, 0);
        CardInfo c残血王国小兵 = context.addToField(1, 1).setBasicHP(1);
        context.startGame();

        random.addNextPicks(0); // 血炼10
        context.proceedOneRound();
        Assert.assertTrue(c残血王国小兵.isDead());
    }

    @Test
    public void test逃跑_基本() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+逃跑");
        context.addToField(0, 0);
        context.addToField(1, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(1, context.getPlayer(1).getHand().size());
    }

    /**
     * 手牌满时送回卡堆
     */
    @Test
    public void test逃跑_手牌满() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+逃跑", "金属巨龙*5");
        context.addToField(0, 0);
        context.addToField(1, 1);
        context.addToHand(2, 1);
        context.addToHand(3, 1);
        context.addToHand(4, 1);
        context.addToHand(5, 1);
        context.addToHand(6, 1);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(5, context.getPlayer(1).getHand().size());
        Assert.assertEquals(1, context.getPlayer(1).getDeck().size());
    }

    /**
     * 物理伤害即使杀死卡牌逃跑也会发动
     */
    @Test
    public void test逃跑_物理伤害_杀死() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像", "占位符+逃跑");
        context.addToField(0, 0);
        context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(1, context.getPlayer(1).getHand().size());
    }

    /**
     * 魔法伤害不会触发逃跑
     */
    @Test
    public void test逃跑_魔法伤害() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+火球1", "占位符", "占位符+逃跑");
        context.addToField(0, 0);
        context.addToField(1, 1);
        CardInfo c占位符2 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(1).addNextNumbers(0); // 火球1
        context.proceedOneRound();
        Assert.assertEquals(2, context.getPlayer(1).getField().size());
        Assert.assertEquals(0, context.getPlayer(1).getHand().size());
        Assert.assertEquals(25, 5000 - c占位符2.getHP());
    }

    /**
     * 魔法伤害能杀死逃跑卡
     */
    @Test
    public void test逃跑_魔法伤害_杀死() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+火球1", "占位符+逃跑");
        context.addToField(0, 0);
        CardInfo c占位符 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        random.addNextPicks(0).addNextNumbers(0); // 火球1
        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(1).getField().size());
        Assert.assertEquals(0, context.getPlayer(1).getHand().size());
        Assert.assertTrue(c占位符.isDead());
    }

    /**
     * 燕返不会触发逃跑
     */
    @Test
    public void test逃跑_燕返() {
        SkillTestContext context = prepare(50, 50, "秘银巨石像+逃跑", "秘银巨石像+燕返");
        CardInfo c秘银巨石像1 = context.addToField(0, 0).setBasicHP(2);
        CardInfo c秘银巨石像2 = context.addToField(1, 1).setBasicHP(2);
        context.startGame();

        context.proceedOneRound();
        Assert.assertEquals(0, context.getPlayer(0).getField().size());
        Assert.assertEquals(0, context.getPlayer(0).getHand().size());
        Assert.assertTrue(c秘银巨石像1.isDead());
        Assert.assertTrue(c秘银巨石像2.isDead());
    }
}
