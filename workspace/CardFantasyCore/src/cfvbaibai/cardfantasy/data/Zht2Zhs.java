package cfvbaibai.cardfantasy.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public final class Zht2Zhs {

    private static Zht2Zhs me;
    
    public static synchronized Zht2Zhs getInstance() {
        if (me == null) {
            me = new Zht2Zhs();
        }
        return me;
    }
    
    private Map <Character, String> dict;
    
    private Zht2Zhs() {
        this.dict = new HashMap <Character, String>();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("cfvbaibai/cardfantasy/data/zht-zhs.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    System.err.println("Invalid line in zht-zhs.txt: " + line);
                    continue;
                }
                String key = parts[1];
                if (key == null || key.length() != 1) {
                    System.err.println("Empty key at line: " + line);
                    continue;
                }
                String value = parts[0];
                if (value == null || value.length() == 0) {
                    System.err.println("Empty value at line: " + line);
                    continue;
                }
                if (this.dict.containsKey(key)) {
                    System.err.println("Duplicate key in zht-zhs.txt: " + key);
                    continue;
                }
                this.dict.put(key.charAt(0), value);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding UTF-8: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public String convert(String text) {
        if (text == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); ++i) {
            Character c = text.charAt(i);
            if (this.dict.containsKey(c)) {
                sb.append(this.dict.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
