package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Combination { 
    @SafeVarargs
    public static <T> List <List <T>> calculate(int n, T ... elements) {
        List <T> elementsArg = new ArrayList<T>(elements.length);
        for (T element : elements) {
            elementsArg.add(element);
        }
        return calculate(n, elementsArg);
    }
    public static <T> List <List <T>> calculate(int n, List <T> elements) {
        if (n == 0 || elements.size() == 0) {
            return new ArrayList <List <T>>();
        }
             
        List <List <T>> result = new ArrayList <List <T>>(); 
        int m = elements.size(); 
        if (m < n) {
            throw new IllegalArgumentException("Error! element size (" + m + ") < n (" + n + ")");
        }
        BitSet bs = new BitSet(m); 
        for (int i = 0; i < n; i++) { 
            bs.set(i, true); 
        } 
        do { 
            result.add(generateOne(elements, bs));
        } while (moveNext(bs, m)); 
        return result;
    } 
    
    private static <T> List <T> generateOne(List <T> elements, BitSet bs) {
        List <T> entry = new ArrayList <T> (bs.size()); 
        for (int i = 0; i < bs.size(); ++i) {
            if (bs.get(i)) {
                entry.add(elements.get(i));
            }
        }
        return entry;
    }

    private static boolean moveNext(BitSet bs, int m) { 
        int start = -1; 
        while (start < m) 
            if (bs.get(++start)) 
                break; 
        if (start >= m) 
            return false; 
  
        int end = start; 
        while (end < m) 
            if (!bs.get(++end)) 
                break; 
        if (end >= m) 
            return false; 
        for (int i = start; i < end; i++) 
            bs.set(i, false); 
        for (int i = 0; i < end - start - 1; i++) 
            bs.set(i); 
        bs.set(end); 
        return true; 
    }
}
