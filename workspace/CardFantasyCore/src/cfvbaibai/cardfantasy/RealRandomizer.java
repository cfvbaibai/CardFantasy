package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RealRandomizer extends Randomizer {

    private static Object syncRoot = new Object();
    private Random random = new Random();
    
    public RealRandomizer() {
    }
    
    public RealRandomizer(GameUI ui) {
        setUI(ui);
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
    public <T> List<T> pickRandom(final List<T> list, int max, boolean excludeNull, List<T> extraExclusion) {
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
            if (extraExclusion != null && extraExclusion.contains(item)) {
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