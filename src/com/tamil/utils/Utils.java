package com.tamil.utils;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description :
 *
 */

public class Utils {

    public static String getElapsedTime(Long startTime) {
        Long endTime = System.currentTimeMillis();
        long milliseconds = endTime - startTime;
        int hours   = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        String formatted_time = String.format("%02d h : %02d m : %02d s", hours, minutes, seconds);
        return "(" + formatted_time + ")";
    }
}
