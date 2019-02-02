package com.citu.timetrackingsystem.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    public static String getDateFormattedInISO8601() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(new Date());
    }

    public static String getDateFormattedInMMddyy(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yy", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return new SimpleDateFormat("MM-dd-yy").format(date);
    }

    public static String getDateFormattedInMMddyy1(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    public static String getDateFormattedInTimeAM_PM(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDateFormattedInHHMMss() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        String AM_PM = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        return String.format("%02d:%02d:%02d", hours, minutes, seconds) + " " + AM_PM;
    }

    public static Date getDateFromISO8601(String iso80601) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return simpleDateFormat.parse(iso80601);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

