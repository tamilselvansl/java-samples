package com.tamil.utils;

import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


// Output : Wed, Jun 03 2015 18:15:17 GMT+05:30

public class GMTDateFormatter {

    private String getGMTtime() {
        String formationType = "EEE, MMM dd yyyy HH:mm:ss";
        Date localTime = new Date();
        TimeZone tz = TimeZone.getDefault();
        DateFormat converter = new SimpleDateFormat(formationType);
        converter.setTimeZone(tz);
        int offset = tz.getRawOffset();
        String text = String.format("%s%02d:%02d", offset >= 0 ? "+" : "-", offset / 3600000, (offset / 60000) % 60);
        return converter.format(localTime) + " GMT" + text;
    }

    public static void main(String[] args) {
        GMTDateFormatter dateTest = new GMTDateFormatter();
        System.out.println(dateTest.getGMTtime());
    }
}
