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
}
