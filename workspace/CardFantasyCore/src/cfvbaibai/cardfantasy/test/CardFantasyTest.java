package cfvbaibai.cardfantasy.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;

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
}
