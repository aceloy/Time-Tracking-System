package com.citu.timetrackingsystem.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    public static String getISO8601Date() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    public static String getDateInMMddyy() {
        return new SimpleDateFormat("MM-dd-yy").format(new Date());
    }

    public static String getDateInMMddyy1() {
        return new SimpleDateFormat("MM/dd/yy").format(new Date());
    }

    public static String getCurrentTimeInHHMMss() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        String AM_PM = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        return String.format("%02d:%02d:%02d", hours, minutes, seconds) + " " + AM_PM;
    }
}

