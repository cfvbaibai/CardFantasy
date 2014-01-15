package cfvbaibai.cardfantasy.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {

    private Utils() {
        // TODO Auto-generated constructor stub
    }

    private static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentDateTime() {
        return DATE_TIME_FORMAT.format(new Date());
    }
    public static String getCurrentDate() {
        return DATE_FORMAT.format(new Date());
    }
    public static String toString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String getAllMessage(Throwable e) {
        if (e == null) {
            return "";
        }
        Throwable current = e;
        String message = "";
        while (true) {
            message += current.getMessage() + ";";
            if (current.getCause() == null) {
                break;
            }
            current = current.getCause();
        }
        return message;
    }
}
