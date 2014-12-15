package cfvbaibai.cardfantasy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public abstract class Randomizer {
    public abstract int next(int min, int max);
    public abstract void shuffle(List<?> list);
    public GameUI ui;
    
    protected Randomizer(GameUI ui) {
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
    
    public static class RealRandomizer extends Randomizer {

        private static Object syncRoot = new Object();
        private Random random = new Random();
        
        protected RealRandomizer(GameUI ui) {
            super(ui);
        }

        @Override
        public int next(int min, int max) {
            synchronized (syncRoot) {
                return random.nextInt(max - min) + min;
            }
        }

        @Override
        public void shuffle(List<?> list) {
            Collections.shuffle(list, random);
        }
    }
    
    public static class StaticRandomizer extends Randomizer {

        protected StaticRandomizer(GameUI ui) {
            super(ui);
        }
        
        private Queue<Integer> nextNumbers = new LinkedList<Integer>();
        
        /**
         * Add next numbers for randomizer.
         * @param numbers Should be numbers between 0 and 999.
         */
        public void addNextNumbers(int ... numbers) {
            for (int i = 0; i < numbers.length; ++i) {
                nextNumbers.add(numbers[i]);
            }
        }

        @Override
        public int next(int min, int max) {
            Integer nextNumber = nextNumbers.poll();
            if (nextNumber == null) {
                throw new CardFantasyRuntimeException("Not enough next numbers!");
            }
            int distance = max - min;
            return nextNumber * distance / 1000 + min;
        }

        @Override
        public void shuffle(List<?> list) {
            // Do nothing...
        }
        
    }
}
