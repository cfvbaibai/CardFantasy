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
            throw new IllegalArgumentException("Error： element size (" + m + ") < n (" + n + ")");
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
    
    /**说明 这个方法不太容易理解，也是组合的核心算法,下面进行简单的讲解. 
     * 1、start是从第一个true片段作为起始位，end作为截止位 
     * 2、第一个true片段都设置成false 
     * 3、数组从0下标起始到以第一个true片段元素数量减一为下标的位置都置true 
     * 4、把第一个true片段end截止位置true 
     * 
     * @param bs 数组是否显示的标志位 
     * @param m 数组长度 
     * @return boolean 是否还有其他组合 
     */
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
