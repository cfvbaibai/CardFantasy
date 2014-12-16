package cfvbaibai.cardfantasy.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DeckStartupInfo;

@RunWith(Suite.class)
@SuiteClasses({ RacialBufferTest.class })
public class FeatureValidationTests {
    public static FeatureTestContext prepare(int playerALevel, int playerBLevel, String ... cards) {
        GameEngine engine = TestGameBuilder.buildEmptyGameForTest(50, 50);
        DeckStartupInfo dsi = DeckBuilder.build(cards);
        FeatureTestContext context = new FeatureTestContext();
        context.setEngine(engine);
        context.setDsi(dsi);
        return context;
    }
}
