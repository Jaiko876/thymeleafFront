package qas.uicontroller.service;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DataParser {
    public Timestamp parseData(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = sdf.parse(string.replace("T", " "));
        Timestamp ts = new Timestamp(parse.getTime());
        return ts;
    }
    public Timestamp minData() {
        final long duration = ((59 * 60) + 59) * 1000;

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts.setTime(ts.getTime() + duration);
        System.out.println(ts.toLocalDateTime());
        return ts;
    }
    public Timestamp maxData() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        cal.add(Calendar.DAY_OF_WEEK, 360);
        ts.setTime(cal.getTime().getTime()); // or
        return ts;
    }
}
