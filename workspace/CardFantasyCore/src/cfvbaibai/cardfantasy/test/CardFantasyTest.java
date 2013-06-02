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

    @Test
    public void testGameBasic() {
        GameResult result = GameBuilder.play(
                PlayerBuilder.build("TOM", 10, "城镇弓箭兵-10*2"),
                PlayerBuilder.build("JERRY", 10, "城镇长矛兵-10*5"));
        assertEquals("TOM", result.getWinner().getId());
        assertEquals(GameEndCause.ALL_CARDS_DIE, result.getCause());
    }
}
