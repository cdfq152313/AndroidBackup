package com.example.denny.ticketparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by denny on 2017/8/8.
 */

public class JulianToFormatDate {
    static String pattern = "yyyy/MM/dd";

    public static String convert(int julian){
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            int year = calendar.get(Calendar.YEAR);
            String yearStart = String.format("%d/01/01", year);
            Date yearStartDate = dateFormat.parse(yearStart);
            calendar.setTime(yearStartDate);
            calendar.add(Calendar.DAY_OF_MONTH, julian-1);
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            return "2016/01/01";
        }
    }
}
