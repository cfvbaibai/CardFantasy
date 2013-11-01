package cfvbaibai.cardfantasy.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {

    private Utils() {
        // TODO Auto-generated constructor stub
    }

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDateTime() {
        return DATE_FORMAT.format(new Date());
    }
    
    public static String toString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
