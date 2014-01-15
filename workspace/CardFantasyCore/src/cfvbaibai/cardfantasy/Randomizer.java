package cfvbaibai.cardfantasy;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Randomizer {
    private static Random random = new Random();

    public GameUI ui;
    private static Object syncRoot = new Object();

    public Randomizer(GameUI ui) {
        this.ui = ui;
    }

    /**
     * Return a random number N for [0, 100).
     * 
     * @return
     */
    public boolean roll100(int rate) {
        int dice = next(0, 100);
        if (ui != null) {
            ui.roll100(dice, rate);
        }
        return dice < rate;
    }

    public int next(int min, int max) {
        synchronized (syncRoot) {
            return random.nextInt(max - min) + min;
        }
    }

    public void shuffle(List<?> list) {
        Collections.shuffle(list, random);
    }
}
