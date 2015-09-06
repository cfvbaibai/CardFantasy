package cfvbaibai.cardfantasy.test.func;

import org.junit.Test;

import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.test.TestGameBuilder;

public class SpecificCardTest {
    /**
     * BUG: 陨星魔法使2张会出错
     */
    @Test
    public void test陨星魔法使_两张() {
        TestGameBuilder.play(
            PlayerBuilder.build(true, "【A】", 75, "陨星魔法使*2","永冻","冰封","灼魂","陨星"),
            PlayerBuilder.build(true, "【B】", 75, "瓦尔基里英灵-5*5","降临天使*2","凤凰*3","冰封","永冻","雷盾","春风"));
    }
}
