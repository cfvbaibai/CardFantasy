package cfvbaibai.cardfantasy.test.func;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import cfvbaibai.cardfantasy.StaticRandomizer;

public class FeatureValidationTest {
    protected StaticRandomizer random = FeatureValidationTests.getRandom();

    @Rule
    public TestName testName = new TestName();

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
