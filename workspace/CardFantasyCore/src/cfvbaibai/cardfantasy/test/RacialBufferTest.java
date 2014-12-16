package cfvbaibai.cardfantasy.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.StageInfo;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DeckStartupInfo;

public class RacialBufferTest {
    private static Randomizer.StaticRandomizer random;
    
    @BeforeClass
    public static void Initialize() {
        random = new Randomizer.StaticRandomizer();
        Randomizer.registerRandomizer(random);
    }
    
    @After
    public void AfterTest() {
        random.reset();
    }

    @Test
    public void Test种族之力_暴击_背刺() {
        random.addNextNumbers(0); // 保证大剑圣的暴击

        GameEngine engine = TestGameBuilder.buildEmptyGameForTest(50, 50);

        Player playerA = engine.getStage().getPlayers().get(0);

        DeckStartupInfo dsi = DeckBuilder.build("隐世先知", "大剑圣", "凤凰*2");
        playerA.getHand().addCard(dsi.getCardInfo(0, playerA)).setSummonDelay(0);
        playerA.getHand().addCard(dsi.getCardInfo(1, playerA)).setSummonDelay(0);

        Player playerB = engine.getStage().getPlayers().get(1);
        playerB.getField().addCard(dsi.getCardInfo(2, playerB));
        CardInfo c凤凰2 = playerB.getField().addCard(dsi.getCardInfo(3, playerB));

        engine.getStage().gameStarted();

        int c凤凰2OriginalHP = c凤凰2.getHP();
        int expectedDamage = ((int)((560 /* Lv10 大剑圣 */ + 175 /* 王国之力 */) * 1.8 /* 暴击 */)) + 200 /* 背刺 */;
        engine.proceedOneRound();
        int actualDamage = c凤凰2OriginalHP - c凤凰2.getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }
    
    @Test
    public void Test种族之力_削弱() {
        random.addNextPick(0).addNextNumbers(0); // 保证独眼巨人摧毁隐世先知

        GameEngine engine = TestGameBuilder.buildEmptyGameForTest(50, 50);
        DeckStartupInfo dsi = DeckBuilder.build("隐世先知-5", "大剑圣", "独眼巨人");
        StageInfo stage = engine.getStage();
        Player playerA = stage.getPlayers().get(0);
        playerA.getHand().addCard(dsi.getCardInfo(0, playerA)).setSummonDelay(0);
        CardInfo c大剑圣 = playerA.getHand().addCard(dsi.getCardInfo(1, playerA)).setSummonDelay(0);

        Player playerB = stage.getPlayers().get(1);
        playerB.getHand().addCard(dsi.getCardInfo(2, playerB)).setSummonDelay(0);

        stage.gameStarted();
        engine.proceedOneRound();
        engine.proceedOneRound();

        // 削弱造成的DEBUFF会被种族之力抵消，种族之力消失后，原本攻击力不会下降
        Assert.assertEquals(560 /* Lv10 大剑圣 */, c大剑圣.getCurrentAT());
    }

    @Test
    public void Test种族之力_瘟疫() {
        GameEngine engine = TestGameBuilder.buildEmptyGameForTest(50, 50);
        DeckStartupInfo dsi = DeckBuilder.build("隐世先知-5", "大剑圣", "木乃伊法老王", "奇美拉");
        StageInfo stage = engine.getStage();

        Player playerA = stage.getPlayers().get(0);
        playerA.getHand().addCard(dsi.getCardInfo(0, playerA)).setSummonDelay(0);
        CardInfo c大剑圣 = playerA.getHand().addCard(dsi.getCardInfo(1, playerA)).setSummonDelay(0);
        
        Player playerB = stage.getPlayers().get(1);
        playerB.getHand().addCard(dsi.getCardInfo(2, playerB)).setSummonDelay(0);
        
        stage.gameStarted();
        random.addNextNumbers(1000); // 大剑圣暴击
        engine.proceedOneRound();

        random.addNextNumbers(1000); // 大剑圣闪避
        engine.proceedOneRound();

        random.addNextNumbers(1000); // 大剑圣暴击
        engine.proceedOneRound();

        playerB.getHand().addCard(dsi.getCardInfo(3, playerB)).setSummonDelay(0);
        random.addNextPick(0, 0); // 奇美拉摧毁, 奇美拉火球
        random.addNextNumbers(1000, 1000);  // 大剑圣闪避, 奇美拉火球
        engine.proceedOneRound();
        
        Assert.assertEquals(560 - 25 * 2 /* Lv10 大剑圣受到两次瘟疫，每次25点 */, c大剑圣.getCurrentAT());
    }
}
