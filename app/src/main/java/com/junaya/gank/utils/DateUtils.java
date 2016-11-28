package com.junaya.gank.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aya on 2016/11/25.
 */

public class DateUtils {
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date){
        return format.format(date);
    }

    public static Date parseDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // format Gank publishAt
    public static String formatPublish(String dateStr){
        return  formatDate(parseDate(dateStr));
    }
}
