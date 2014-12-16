package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import cfvbaibai.cardfantasy.game.DummyGameUI;

public abstract class Randomizer {
    private static Randomizer registeredRandomizer;
    public static void registerRandomizer(Randomizer randomizer) {
        registeredRandomizer = randomizer;
    }

    public static Randomizer getRandomizer() {
        if (registeredRandomizer == null) {
            registeredRandomizer = new Randomizer.RealRandomizer();
        }
        return registeredRandomizer;
    }

    public abstract int next(int min, int max);
    public abstract void shuffle(List<?> list);
    public abstract <T> List<T> pickRandom(final List<T> list, int max, boolean excludeNull, T extraExclusion);

    private GameUI ui;
    
    protected Randomizer() {
        this.ui = new DummyGameUI();
    }
    
    public Randomizer setUI(GameUI ui) {
        this.ui = ui;
        return this;
    }
    
    protected GameUI getUI() {
        return this.ui;
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
        
        public RealRandomizer() {
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

        @Override
        public <T> List<T> pickRandom(final List<T> list, int max, boolean excludeNull, T extraExclusion) {
            if (max == 0) {
                return new ArrayList<T>();
            }
            if (max < 0) {
                max = list.size();
            }
            final List<T> clone = new ArrayList<T>(list);
            shuffle(clone);
            List<T> result = new LinkedList<T>();
            for (T item : clone) {
                if (item == null && excludeNull) {
                    continue;
                }
                if (item == extraExclusion) {
                    continue;
                }
                result.add(item);
                if (result.size() == max) {
                    break;
                }
            }
            Collections.sort(result, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return list.indexOf(o1) - list.indexOf(o2);
                }
            });
            return result;
        }
    }
    
    public static class StaticRandomizer extends Randomizer {

        public StaticRandomizer() {
        }
        
        private Queue<Integer> nextNumbers = new LinkedList<Integer>();
        private Queue<Integer> nextPicks = new LinkedList<Integer>();

        /**
         * Add next numbers for randomizer.
         * @param numbers Should be numbers between 0 and 999.
         */
        public StaticRandomizer addNextNumbers(int ... numbers) {
            for (int i = 0; i < numbers.length; ++i) {
                nextNumbers.add(numbers[i]);
            }
            return this;
        }
        
        public StaticRandomizer addNextPicks(int ... picks) {
            for (int i = 0; i < picks.length; ++i) {
                nextPicks.add(picks[i]);
            }
            return this;
        }

        @Override
        public int next(int min, int max) {
            getUI().showMessage("nextNumber");
            Integer nextNumber = nextNumbers.poll();
            if (nextNumber == null) {
                throw new CardFantasyRuntimeException("Not enough next numbers!");
            }
            int distance = max - min;
            return nextNumber * distance / 1000 + min;
        }

        @Override
        public void shuffle(List<?> list) {
            // TODO: Still random.
            Collections.shuffle(list);
        }

        @Override
        public <T> List<T> pickRandom(List<T> list, int max, boolean excludeNull, T extraExclusion) {
            getUI().showMessage("pickRandom");
            if (list == null) {
                throw new IllegalArgumentException("list cannot be null.");
            }
            if (max == 0) {
                return new ArrayList<T>();
            }
            if (max < 0) {
                max = list.size();
            }
            List<T> result = new ArrayList<T>();
            Integer nextPick = null; 
            while (max > 0) {
                nextPick = nextPicks.poll();
                if (nextPick == null) {
                    throw new CardFantasyRuntimeException("Not enough next picks!");
                }
                if (nextPick >= list.size()) {
                    throw new CardFantasyRuntimeException("Invalid next pick " + nextPick + ". List size: " + list.size());
                }
                result.add(list.get(nextPick));
                --max;
            }
            return result;
        }

        public void reset() {
            this.nextNumbers.clear();
            this.nextPicks.clear();
        }
    }
}
