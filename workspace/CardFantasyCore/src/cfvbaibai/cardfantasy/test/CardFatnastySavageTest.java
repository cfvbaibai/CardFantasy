package cfvbaibai.cardfantasy.test;

import org.junit.Test;

import cfvbaibai.cardfantasy.data.RuneData;

public class CardFatnastySavageTest {

    @Test
    public void 白斑蜘蛛vs黑夜蝙蝠() {
        GameBuilder.play5v5("白斑蜘蛛", "黑夜蝙蝠");
    }

    @Test
    public void 瘟疫蜘蛛vs山羊人盾士() {
        GameBuilder.play5v5("瘟疫蜘蛛", "山羊人盾士");
    }

    @Test
    public void 牛头人卫士vs食人魔术士() {
        GameBuilder.play5v5("牛头人卫士", "食人魔术士");
    }

    @Test
    public void 独眼巨人vs食人魔术士() {
        GameBuilder.play5v5("独眼巨人", "食人魔术士");
    }

    @Test
    public void 独眼巨人v哥布林术士() {
        GameBuilder.play5v5("独眼巨人", "哥布林术士");
    }

    @Test
    public void 九头妖蛇v远古蝎皇() {
        GameBuilder.play5v5("九头妖蛇", "远古蝎皇");
    }

    @Test
    public void 九头妖蛇v远古蝎皇_荒芜() {
        GameBuilder.play5v5withRunes("九头妖蛇", RuneData.荒芜, "远古蝎皇", RuneData.荒芜);
    }
}
