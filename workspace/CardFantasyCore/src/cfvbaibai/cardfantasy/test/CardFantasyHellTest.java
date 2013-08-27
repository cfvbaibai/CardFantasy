package cfvbaibai.cardfantasy.test;

import org.junit.Test;

public class CardFantasyHellTest {
    @Test
    public void 血炼巫妖vs暗夜魔影() {
        TestGameBuilder.play5v5("血炼巫妖", "暗夜魔影");
    }

    @Test
    public void 毁灭之龙vs暗夜魔影() {
        TestGameBuilder.play5v5("毁灭之龙", "暗夜魔影");
    }

    @Test
    public void 毁灭之龙vs灵魂收割者() {
        TestGameBuilder.play5v5("毁灭之龙", "灵魂收割者");
    }

    @Test
    public void 骷髅法师vs骷髅法师() {
        TestGameBuilder.play5v5("骷髅法师", "骷髅法师");
    }
    
    @Test
    public void 骷髅法师vs梦魇() {
        TestGameBuilder.play5v5("骷髅法师", "梦魇");
    }
    
    @Test
    public void 毁灭之龙vs堕落天使() {
        TestGameBuilder.play5v5("毁灭之龙", "堕落天使");
    }}
