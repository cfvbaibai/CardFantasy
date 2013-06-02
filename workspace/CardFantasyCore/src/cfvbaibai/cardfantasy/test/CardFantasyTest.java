package cfvbaibai.cardfantasy.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;

public class CardFantasyTest {

    private PlayerInfo player1Info;
    private PlayerInfo player2Info;
    private CardDataStore cardDataStore;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        this.cardDataStore = CardDataStore.loadDefault();
        this.player1Info = new PlayerInfo("Tom", 1, getTestCards("城镇弓箭兵", 5));
        this.player2Info = new PlayerInfo("Jerry", 1, getTestCards("城镇长矛兵", 5));
    }

    private Card[] getTestCards(String name, int count) {
        Card[] cards = new Card[count];
        for (int i = 0; i < cards.length; ++i) {
            cards[i] = new Card(cardDataStore.getCardInfo(name));
            cards[i].growToLevel(10);
        }
        return cards;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPlayGame() {
        System.out.println("testPlayGame");
        GameEngine engine = new GameEngine(new TestGameUI(), new Rule(3, 50));
        engine.RegisterPlayers(player1Info, player2Info);
        GameResult result = engine.playGame();
        assertEquals("Tom", result.getWinner().getId());
        assertEquals(GameEndCause.ALL_CARDS_DIE, result.getCause());
    }

}
