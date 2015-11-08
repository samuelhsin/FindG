package com.findg.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by samuelhsin on 2015/11/8.
 */
public abstract class DateUtils {

    public static final String defaultDateTimeFormat = "yyyy-MM-dd HH:mm:SS";

    public static Date now() {
        return new Date();
    }

    public static Date nowCST() {
        Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone("CST");
        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }
        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }
        return calendar.getTime();
    }

    public static Date nowGMT() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        return calendar.getTime();
    }

    public static String getDateTimeStr(Date currentTime, String format) {
        DateFormat date = new SimpleDateFormat(format);
        return date.format(currentTime);
    }

    public static String getDateTimeStr(Date currentTime) {
        return getDateTimeStr(currentTime, defaultDateTimeFormat);
    }

    /**
     * return format: GMT+05:30
     *
     * @return
     */
    public String getGMTTimezoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT" + (offsetInMillis >= 0 ? "+" : "-") + offset;
        return offset;
    }

}
