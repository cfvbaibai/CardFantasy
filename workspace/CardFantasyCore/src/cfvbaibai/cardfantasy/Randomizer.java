package cfvbaibai.cardfantasy;

import java.util.Random;

public class Randomizer {
    private static Random random = new Random();
    public static Random getRandom() {
        return random;
    }
    
    /**
     * Return a random number N for [0, 100).
     * @return
     */
    public static int roll100() {
        return random.nextInt(100);
    }
}
