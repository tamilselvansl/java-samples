package com.tamil.utils;

import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Schedule test to print time
 */

public class ScheduleTester {

    public static void main(String[] args) {
        // Get the scheduler
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Get a handle, starting now, with a 10 second delay
        final ScheduledFuture<?> timeHandle = scheduler.scheduleAtFixedRate(
                new TimePrinter(System.out), 0, 1, java.util.concurrent.TimeUnit.SECONDS);

        // Schedule the event, and run for 1 hour (60 * 60 seconds)
        scheduler.schedule(new Runnable() {
            public void run() {
                timeHandle.cancel(false);
            }
        }, 60 * 60, java.util.concurrent.TimeUnit.SECONDS);


        /**
         * On some platforms, you'll have to setup this infinite loop to see output
         while (true) { }
         */
    }
    static class TimePrinter implements Runnable {
        PrintStream out;
        TimePrinter(PrintStream out) {
            this.out = out;
        }

        public void run() {
            out.print(new Date());
        }
    }
}
