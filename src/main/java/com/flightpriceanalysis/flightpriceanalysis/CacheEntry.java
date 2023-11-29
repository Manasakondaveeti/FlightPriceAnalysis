package com.flightpriceanalysis.flightpriceanalysis;

public class CacheEntry {
    private String response;
    private long timestamp;

    public CacheEntry(String response, long timestamp) {
        this.response = response;
        this.timestamp = timestamp;
    }

    public String getResponse() {
        return response;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
