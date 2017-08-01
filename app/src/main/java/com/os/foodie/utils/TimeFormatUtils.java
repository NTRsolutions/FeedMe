package com.os.foodie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFormatUtils {

    public static String changeTimeFormat(String d, String from, String to) {
        String date_str = "";
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(from, Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(to, Locale.ENGLISH);

        try {
            date1 = simpleDateFormat.parse(d);
            date_str = simpleDateFormat1.format(date1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date_str;
    }
}