package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.List;

public final class C {
    public static <T> List<T> toList(T[] array) {
        List <T> result = new ArrayList <T>();
        for (int i = 0; i < array.length; ++i) {
            result.add(array[i]);
        }
        return result;
    }
}
