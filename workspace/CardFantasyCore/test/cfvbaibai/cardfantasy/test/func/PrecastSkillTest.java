package cfvbaibai.cardfantasy.test.func;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class PrecastSkillTest extends SkillValidationTest {
    @Test
    public void test先机凋零真言_普通() {
        SkillTestContext context = prepare(50, 50, "占位符+先机凋零真言5", "秘银巨石像*2");
        context.addToHand(0, 0).setSummonDelay(4);
        CardInfo c秘银巨石像1 = context.addToField(1, 1);
        CardInfo c秘银巨石像2 = context.addToField(2, 1);
        context.startGame();

        random.addNextPicks(0, 1);
        random.addNextPicks(0, 1);
        context.proceedOneRound();
        int currentAT = 660 - 660 * 9 / 100;
        int currentHP = 1400 - 1400 * 9 / 100;
        Assert.assertEquals(currentAT, c秘银巨石像1.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像1.getHP());
        Assert.assertEquals(currentAT, c秘银巨石像2.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像2.getHP());

        context.proceedOneRound();
        Assert.assertEquals(currentAT, c秘银巨石像1.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像1.getHP());
        Assert.assertEquals(currentAT, c秘银巨石像2.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像2.getHP());

        context.proceedOneRound();
        currentAT = currentAT - currentAT * 9 / 100;
        currentHP = currentHP - 1400 * 9 / 100;
        Assert.assertEquals(currentAT, c秘银巨石像1.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像1.getHP());
        Assert.assertEquals(currentAT, c秘银巨石像2.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像2.getHP());

        context.proceedOneRound();
        Assert.assertEquals(currentAT, c秘银巨石像1.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像1.getHP());
        Assert.assertEquals(currentAT, c秘银巨石像2.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像2.getHP());

        context.proceedOneRound();
        Assert.assertEquals(currentAT, c秘银巨石像1.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像1.getHP());
        Assert.assertEquals(currentAT, c秘银巨石像2.getCurrentAT());
        Assert.assertEquals(currentHP, c秘银巨石像2.getHP());
    }
}
