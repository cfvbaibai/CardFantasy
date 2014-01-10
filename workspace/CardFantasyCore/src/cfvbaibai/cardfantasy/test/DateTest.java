package cfvbaibai.cardfantasy.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DateTest {
    @Test
    public void testTimeZone() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        TimeZone defaultTimeZone = calendar.getTimeZone();
        System.out.println("Default TimeZone = " + defaultTimeZone.getDisplayName());
        calendar.setTime(date);
        //calendar.add(Calendar.HOUR_OF_DAY, -8);
        System.out.println("Local now = " + formatter.format(calendar.getTime()));
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC now = " + formatter.format(calendar.getTime()));
    }
}
