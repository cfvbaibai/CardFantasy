package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CardFantasyTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private void game5vs5(String card1, String card2) {
        GameBuilder.play(PlayerBuilder.build("英雄" + card1, 50, card1 + "-10*5"),
                PlayerBuilder.build("英雄" + card2, 50, card2 + "-10*5"));
    }

    @Test
    public void 城镇弓箭兵vs城镇长矛兵() {
        game5vs5("城镇弓箭兵", "城镇长矛兵");
    }

    @Test
    public void 城镇弓箭兵vs城镇巡逻兵() {
        game5vs5("城镇弓箭兵", "城镇巡逻兵");
    }

    @Test
    public void 城镇长矛兵vs城镇巡逻兵() {
        game5vs5("城镇长矛兵", "城镇巡逻兵");
    }

    @Test
    public void 城镇弓箭兵vs城镇突击兵() {
        game5vs5("城镇弓箭兵", "城镇突击兵");
    }

    @Test
    public void 城镇长矛兵vs城镇突击兵() {
        game5vs5("城镇长矛兵", "城镇突击兵");
    }

    @Test
    public void 城镇巡逻兵vs城镇突击兵() {
        game5vs5("城镇巡逻兵", "城镇突击兵");
    }

    @Test
    public void 城镇巡逻兵vs魔法小丑() {
        game5vs5("城镇巡逻兵", "魔法小丑");
    }

    @Test
    public void 城镇弓箭兵vs攻城弩车手() {
        game5vs5("城镇弓箭兵", "攻城弩车手");
    }

    @Test
    public void 城镇弓箭兵vs狙击箭弩兵() {
        game5vs5("城镇弓箭兵", "狙击箭弩兵");
    }

    @Test
    public void 魔法小丑vs狙击箭弩兵() {
        game5vs5("魔法小丑", "狙击箭弩兵");
    }

    @Test
    public void 城镇弓箭兵vs轻甲盾卫兵() {
        game5vs5("城镇弓箭兵", "轻甲盾卫兵");
    }

    @Test
    public void 狙击箭弩兵vs轻甲盾卫兵() {
        game5vs5("狙击箭弩兵", "轻甲盾卫兵");
    }

    @Test
    public void 狙击箭弩兵vs见习魔剑士() {
        game5vs5("狙击箭弩兵", "见习魔剑士");
    }

    @Test
    public void 轻甲盾卫兵vs见习魔剑士() {
        game5vs5("轻甲盾卫兵", "见习魔剑士");
    }

    @Test
    public void 冲锋斩斧兵vs见习魔剑士() {
        game5vs5("冲锋斩斧兵", "见习魔剑士");
    }

    @Test
    public void 冲锋斩斧兵vs见习圣骑() {
        game5vs5("冲锋斩斧兵", "见习圣骑");
    }

    @Test
    public void 牧师vs见习圣骑() {
        game5vs5("牧师", "见习圣骑");
    }

    @Test
    public void 牧师vs圣光信仰者() {
        game5vs5("牧师", "圣光信仰者");
    }

    @Test
    public void 魔法小丑vs猎魔犬() {
        game5vs5("魔法小丑", "猎魔犬");
    }

    @Test
    public void 冲锋斩斧兵vs弩骑兵() {
        game5vs5("冲锋斩斧兵", "弩骑兵");
    }

    @Test
    public void 魔法小丑vs魔剑士() {
        game5vs5("魔法小丑", "魔剑士");
    }

    @Test
    public void 魔法小丑vs重甲骑兵() {
        game5vs5("魔法小丑", "重甲骑兵");
    }

    @Test
    public void 喷火装甲车手vs重甲骑兵() {
        game5vs5("喷火装甲车手", "重甲骑兵");
    }

    @Test
    public void 魔法小丑vs火焰乌鸦() {
        game5vs5("魔法小丑", "火焰乌鸦");
    }

    @Test
    public void 城镇弓箭兵vs狮鹫() {
        game5vs5("城镇弓箭兵", "狮鹫");
    }

    @Test
    public void 火焰乌鸦vs魔导师() {
        game5vs5("火焰乌鸦", "魔导师");
    }

    @Test
    public void 城镇弓箭兵vs魔导师() {
        game5vs5("城镇弓箭兵", "魔导师");
    }

    @Test
    public void 狙击箭弩兵vs魔法结晶体() {
        game5vs5("狙击箭弩兵", "魔法结晶体");
    }

    @Test
    public void 魔剑士vs魔法结晶体() {
        game5vs5("魔剑士", "魔法结晶体");
    }

    @Test
    public void 重甲骑兵vs魔法结晶体() {
        game5vs5("重甲骑兵", "魔法结晶体");
    }

    @Test
    public void 魔剑士vs圣骑士() {
        game5vs5("魔剑士", "圣骑士");
    }

    @Test
    public void 魔导师vs圣骑士() {
        game5vs5("魔导师", "圣骑士");
    }

    @Test
    public void 狙击箭弩兵vs圣骑士() {
        game5vs5("狙击箭弩兵", "圣骑士");
    }

    @Test
    public void 火焰乌鸦vs暴雪召唤士() {
        game5vs5("火焰乌鸦", "暴雪召唤士");
    }

    @Test
    public void 暴雪召唤士vs东方僧人() {
        game5vs5("暴雪召唤士", "东方僧人");
    }

    @Test
    public void 牧师vs皇家雄狮() {
        game5vs5("牧师", "皇家雄狮");
    }

    @Test
    public void 巨型电鳗vs海盗船长() {
        game5vs5("巨型电鳗", "海盗船长");
    }

    @Test
    public void 光影魔术师vs圣骑士() {
        GameBuilder.play(PlayerBuilder.build("英雄光影魔术师", 50, "光影魔术师-10*2", "暴雪召唤士-10*3"),
                PlayerBuilder.build("英雄圣骑士", 50, "圣骑士-10*5"));
    }

    @Test
    public void 喷火装甲车手vs圣堂武士() {
        game5vs5("喷火装甲车手", "圣堂武士");
    }

    @Test
    public void 暴雪召唤士vs圣堂武士() {
        game5vs5("暴雪召唤士", "圣堂武士");
    }

    @Test
    public void 皇家卫队将领vs大主教() {
        game5vs5("皇家卫队将领", "大主教");
    }

    @Test
    public void 皇家卫队将领vs魔法协会长() {
        game5vs5("皇家卫队将领", "魔法协会长");
    }

    @Test
    public void 皇家卫队将领vs魔法协会长_喷火装甲车手() {
        GameBuilder.play(PlayerBuilder.build("英雄皇家卫队将领", 50, "皇家卫队将领-10*5"),
                PlayerBuilder.build("英雄魔法协会长", 50, "魔法协会长-10*2", "暴雪召唤士-10*3"));
    }

    @Test
    public void 荣耀巨人vs大剑圣() {
        game5vs5("荣耀巨人", "大剑圣");
    }

    @Test
    public void 城镇弓箭兵vs大剑圣() {
        game5vs5("城镇弓箭兵", "大剑圣");
    }

    @Test
    public void 时空旅者vs秘银巨石像() {
        game5vs5("时空旅者", "秘银巨石像");
    }

    @Test
    public void 大剑圣vs时空旅者() {
        game5vs5("大剑圣", "时空旅者");
    }

    @Test
    public void 狮鹫骑士vs时空旅者() {
        game5vs5("狮鹫骑士", "时空旅者");
    }

    @Test
    public void 机械飞龙vs时空旅者() {
        game5vs5("机械飞龙", "时空旅者");
    }

    @Test
    public void 狮鹫骑士vs钻石巨石像() {
        game5vs5("狮鹫骑士", "钻石巨石像");
    }

    @Test
    public void 炼金机甲vs钻石巨石像() {
        game5vs5("炼金机甲", "钻石巨石像");
    }

    @Test
    public void 时空旅者vs降临天使() {
        game5vs5("时空旅者", "降临天使");
    }

    @Test
    public void 光明之龙vs城镇弓箭兵() {
        game5vs5("光明之龙", "城镇弓箭兵");
    }

    @Test
    public void 光明之龙vs城镇长矛兵() {
        game5vs5("光明之龙", "城镇长矛兵");
    }

    @Test
    public void 光明之龙vs城镇突击兵() {
        game5vs5("光明之龙", "城镇突击兵");
    }

    @Test
    public void 光明之龙vs魔法小丑() {
        game5vs5("光明之龙", "魔法小丑");
    }

    @Test
    public void 光明之龙vs重甲骑兵() {
        game5vs5("光明之龙", "重甲骑兵");
    }

    @Test
    public void 光明之龙vs喷火装甲车手() {
        game5vs5("光明之龙", "喷火装甲车手");
    }

    @Test
    public void 光明之龙vs皇家雄狮() {
        game5vs5("光明之龙", "皇家雄狮");
    }

    @Test
    public void 光明之龙vs降临天使() {
        game5vs5("光明之龙", "降临天使");
    }

    @Test
    public void 光明之龙vs战神() {
        game5vs5("光明之龙", "战神");
    }
}
