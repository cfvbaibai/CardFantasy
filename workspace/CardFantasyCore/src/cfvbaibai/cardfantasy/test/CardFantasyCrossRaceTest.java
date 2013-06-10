package cfvbaibai.cardfantasy.test;

import org.junit.Test;

public class CardFantasyCrossRaceTest {
    @Test
    public void 反噬and守护() {
        GameBuilder.play(PlayerBuilder.build("【A】", 50, "瘟疫蜘蛛-10*2", "魔剑士-10*3"),
                PlayerBuilder.build("【B】", 50, "城镇弓箭兵-10*5"));
    }

    @Test
    public void 诅咒vs守护() {
        GameBuilder.play5v5("食人魔术士", "魔剑士");
    }
}
