package cfvbaibai.cardfantasy;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Randomizer {
    private static Random random = new Random();
    
    /**
     * Return a random number N for [0, 100).
     * @return
     */
    public static int roll100() {
        return random.nextInt(100);
    }

    public static int next(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static void shuffle(List<?> list) {
        Collections.shuffle(list, random);
    }
}
