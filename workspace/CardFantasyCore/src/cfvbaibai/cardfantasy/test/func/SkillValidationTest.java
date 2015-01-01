package cfvbaibai.cardfantasy.test.func;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.StaticRandomizer;

public class SkillValidationTest {
    protected static StaticRandomizer random;
    protected static Randomizer originalRandomizer;
    @Rule
    public TestName testName = new TestName();

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
