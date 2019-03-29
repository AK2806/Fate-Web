package com.brightstar.trpgfate.component.staticly.datetime;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatetimeConverter {

    public static Date calendar2UtilDate(Calendar calendar) {
        return calendar.getTime();
    }

    public static java.sql.Date calendar2SqlDate(Calendar calendar) {
        return new java.sql.Date(calendar.getTime().getTime());
    }

    public static Timestamp calendar2SqlTimestamp(Calendar calendar) {
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Calendar utilDate2Calendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static Timestamp utilDate2SqlTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static java.sql.Date utilDate2SqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Calendar sqlTimestamp2Calendar(Timestamp timestamp) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(timestamp);
        return calendar;
    }

    public static Date sqlTimestamp2UtilDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    public static java.sql.Date sqlTimestamp2SqlDate(Timestamp timestamp) {
        return new java.sql.Date(timestamp.getTime());
    }

    public static Calendar sqlDate2Calendar(java.sql.Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static Date sqlDate2UtilDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static Timestamp sqlDate2SqlTimestamp(java.sql.Date date) {
        return new Timestamp(date.getTime());
    }
}
