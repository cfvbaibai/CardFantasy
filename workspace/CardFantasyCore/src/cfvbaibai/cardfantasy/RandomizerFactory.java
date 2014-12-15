package cfvbaibai.cardfantasy;

import java.util.LinkedList;
import java.util.Queue;

public class RandomizerFactory {

    public static boolean useFakeRandomizer = false;
    private static Queue<Integer> storedNextNumbers = new LinkedList<Integer>();
    public static void addNextNumbers(int ... nextNumbers) {
        for (int i = 0; i < nextNumbers.length; ++i) {
            storedNextNumbers.add(nextNumbers[i]);
        }
    }
    public static Randomizer getRandomizer(GameUI ui) {
        if (useFakeRandomizer) {
            Randomizer.StaticRandomizer fakeRandomizer = new Randomizer.StaticRandomizer(ui);
            Integer nextNumber = null;
            while ((nextNumber = storedNextNumbers.poll()) != null) {
                fakeRandomizer.addNextNumbers(nextNumber);
            }
            return fakeRandomizer;
        } else {
            return new Randomizer.RealRandomizer(ui);
        }
    }

}
