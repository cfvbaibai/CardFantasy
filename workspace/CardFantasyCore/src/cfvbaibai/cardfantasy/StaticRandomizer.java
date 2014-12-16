package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StaticRandomizer extends Randomizer {

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
        int pickableCount = 0;
        for (int i = 0; i < list.size(); ++i) {
            if ((!excludeNull || list.get(i) != null) && list.get(i) != extraExclusion) {
                ++pickableCount;
            }
        }
        if (max > pickableCount) {
            max = pickableCount;
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