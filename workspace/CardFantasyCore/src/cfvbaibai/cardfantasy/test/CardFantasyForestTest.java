package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CardFantasyForestTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void 翡翠龙vs森林女神() {
        TestGameBuilder.play5v5("翡翠龙", "森林女神");
    }

    @Test
    public void 金属巨龙vs月神弩炮车手() {
        TestGameBuilder.play5v5("金属巨龙", "月神弩炮车手");
    }

    @Test
    public void 翡翠龙vs月神弩炮车手() {
        TestGameBuilder.play5v5("翡翠龙", "月神弩炮车手");
    }

    @Test
    public void 森林丘比特vs月神弩炮车手() {
        TestGameBuilder.play5v5("森林丘比特", "月神弩炮车手");
    }

    @Test
    public void 森林丘比特vs金属巨龙() {
        TestGameBuilder.play5v5("森林丘比特", "金属巨龙");
    }

    @Test
    public void 凤凰vs金属巨龙() {
        TestGameBuilder.play5v5("凤凰", "金属巨龙");
    }

    @Test
    public void 元素灵龙vs金属巨龙() {
        TestGameBuilder.play5v5("元素灵龙", "金属巨龙");
    }

    @Test
    public void 元素灵龙vs凤凰() {
        TestGameBuilder.play5v5("元素灵龙", "凤凰");
    }

    @Test
    public void 小矮人工匠vs密纹骑士() {
        TestGameBuilder.play5v5("小矮人工匠", "密纹骑士");
    }

    @Test
    public void 小矮人工匠vs森林女神() {
        TestGameBuilder.play5v5("小矮人工匠", "森林女神");
    }
    
    @Test
    public void 世界树之灵vs凤凰() {
        TestGameBuilder.play5v5("世界树之灵", "凤凰");
    }
}
