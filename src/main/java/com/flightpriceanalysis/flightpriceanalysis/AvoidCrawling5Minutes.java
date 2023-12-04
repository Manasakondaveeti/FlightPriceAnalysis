package com.flightpriceanalysis.flightpriceanalysis;

public class AvoidCrawling5Minutes {


    public static boolean isWithinTimeWindow(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - timestamp;
        long timeWindow = 5 * 60 * 1000; // 5 minutes in milliseconds

        return timeDifference < timeWindow;
    }

}
