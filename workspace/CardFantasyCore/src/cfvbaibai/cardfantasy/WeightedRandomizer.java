package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.List;

public class WeightedRandomizer <T> {
    private class Range {
        private int weight;
        private T obj;
        public int getWeight() {
            return weight;
        }
        public T getObj() {
            return obj;
        }
        public Range(int weight, T obj) {
            this.weight = weight;
            this.obj = obj;
        }
    }

    private Randomizer random;
    
    private List<Range> ranges;
    private int totalWeight;
    
    public WeightedRandomizer(Randomizer random) {
        if (random == null) {
            throw new IllegalArgumentException("random cannot be null!");
        }
        this.random = random;
        this.totalWeight = 0;
        this.ranges = new ArrayList<Range>();
    }
    
    public void addRange(int weight, T obj) {
        if (weight <= 0) {
            throw new IllegalArgumentException("weight must be positive integer.");
        }
        this.ranges.add(new Range(weight, obj));
        this.totalWeight += weight;
    }
    
    public T pickRandom() {
        if (this.ranges.isEmpty()) {
            throw new CardFantasyRuntimeException("Cannot pick random if no range is added.");
        }
        int candidate = random.next(1, totalWeight);
        int currentRangeLowerBound = 0;
        int currentRangeIndex = 0;
        while (true) {
            currentRangeLowerBound += this.ranges.get(currentRangeIndex).getWeight();
            if (currentRangeLowerBound > candidate) {
                break;
            }
            ++currentRangeIndex;
        }
        return this.ranges.get(currentRangeIndex).getObj();
    }
}
