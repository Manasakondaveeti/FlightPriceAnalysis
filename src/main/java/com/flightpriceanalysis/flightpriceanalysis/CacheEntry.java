package com.flightpriceanalysis.flightpriceanalysis;

import org.springframework.http.ResponseEntity;

public class CacheEntry {

    private boolean fromCache;
    private ResponseEntity<String> response;
    private long timestamp;



    public ResponseEntity<String> getResponse() {
        return response;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public boolean getFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }

    public void setResponse(ResponseEntity<String> response) {
        this.response = response;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
