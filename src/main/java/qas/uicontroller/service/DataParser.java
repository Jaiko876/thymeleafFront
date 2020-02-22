package qas.uicontroller.service;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataParser {
    public Timestamp parseData(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = sdf.parse(string.replace("T", " "));
        Timestamp ts = new Timestamp(parse.getTime());
        return ts;
    }
}
