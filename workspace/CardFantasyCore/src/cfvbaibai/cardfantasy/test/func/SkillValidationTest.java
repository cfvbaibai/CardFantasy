package cfvbaibai.cardfantasy.test.func;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.StaticRandomizer;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DeckStartupInfo;
import cfvbaibai.cardfantasy.test.TestGameBuilder;

public class SkillValidationTest {
    protected static StaticRandomizer random;
    protected static Randomizer originalRandomizer;
    @Rule
    public TestName testName = new TestName();

    public static SkillTestContext prepare(int playerALevel, int playerBLevel, String ... cards) {
        BattleEngine engine = TestGameBuilder.buildEmptyGameForTest(50, 50);
        DeckStartupInfo dsi = DeckBuilder.build(cards);
        SkillTestContext context = new SkillTestContext();
        context.setEngine(engine);
        context.setDsi(dsi);
        return context;
    }

    @BeforeClass
    public static void beforeClass() {
        originalRandomizer = Randomizer.getRandomizer();
        random = new StaticRandomizer();
        Randomizer.registerRandomizer(random);
    }

    @AfterClass
    public static void afterClass() {
        if (originalRandomizer != null) {
            Randomizer.registerRandomizer(originalRandomizer);
        }
    }

    @Before
    public void beforeTest() {
        System.out.println("========");
        System.out.println("======================= " + testName.getMethodName() + " =======================");
        System.out.println("========");
    }

    @After
    public void afterTest() {
        random.reset();
    }
}
