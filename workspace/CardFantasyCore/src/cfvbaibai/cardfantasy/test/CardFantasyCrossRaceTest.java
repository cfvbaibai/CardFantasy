package cfvbaibai.cardfantasy.test;

import org.junit.Test;

import cfvbaibai.cardfantasy.game.PlayerBuilder;

public class CardFantasyCrossRaceTest {
    @Test
    public void 反噬and守护() {
        TestGameBuilder.play(PlayerBuilder.build("【A】", 50, "瘟疫蜘蛛-10*2", "魔剑士-10*3"),
                PlayerBuilder.build("【B】", 50, "城镇弓箭兵-10*5"));
    }

    @Test
    public void 诅咒vs守护() {
        TestGameBuilder.play5v5("食人魔术士", "魔剑士");
    }

    @Test
    public void 皇室舞者vs蜘蛛人女王() {
        TestGameBuilder.play5v5("皇室舞者", "蜘蛛人女王");
    }

    @Test
    public void 机械兵团长vs黑甲铁骑士() {
        TestGameBuilder.play5v5("机械兵团长", "黑甲铁骑士");
    }

    @Test
    public void 刀锋女王vs战斗猛犸象() {
        TestGameBuilder.play5v5("刀锋女王", "战斗猛犸象");
    }
}
