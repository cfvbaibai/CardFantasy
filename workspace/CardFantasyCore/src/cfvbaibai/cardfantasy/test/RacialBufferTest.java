package cfvbaibai.cardfantasy.test;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.RandomizerFactory;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DeckStartupInfo;
import cfvbaibai.cardfantasy.game.PlayerBuilder;

public class RacialBufferTest {
    @Test
    public void Test种族之力_暴击_背刺() {
        RandomizerFactory.useFakeRandomizer = true;
        RandomizerFactory.addNextNumbers(0); // 保证大剑圣的暴击

        GameEngine engine = TestGameBuilder.build(
            PlayerBuilder.build(true, "PlayerA", 50, ""),
            PlayerBuilder.build(true, "PlayerB", 50, ""));

        Player playerA = engine.getStage().getPlayers().get(0);

        DeckStartupInfo dsi = DeckBuilder.build("隐世先知", "大剑圣", "凤凰*2");
        CardInfo c隐世先知 = new CardInfo(dsi.getCards().get(0), playerA);
        playerA.getHand().addCard(c隐世先知);
        c隐世先知.setSummonDelay(0);
        CardInfo c大剑圣 = new CardInfo(dsi.getCards().get(1), playerA);
        playerA.getHand().addCard(c大剑圣);
        c大剑圣.setSummonDelay(0);

        Player playerB = engine.getStage().getPlayers().get(1);
        playerB.getField().addCard(new CardInfo(dsi.getCards().get(2), playerB));
        playerB.getField().addCard(new CardInfo(dsi.getCards().get(3), playerB));

        engine.getStage().gameStarted();
        engine.getStage().setActivePlayerNumber(0);

        int c凤凰2OriginalHP = playerB.getField().getCard(1).getHP();
        int expectedDamage = ((int)((c大剑圣.getInitAT() + 175) * 1.8)) + 200; // 背刺 + 王国之力
        engine.proceedOneRound();
        int actualDamage = c凤凰2OriginalHP - playerB.getField().getCard(1).getHP();
        Assert.assertEquals(expectedDamage, actualDamage);
    }
}
