package com.flightpriceanalysis.flightpriceanalysis;

public class ErrorResponse {



        private String message;
        private String details;

    public ErrorResponse(String internalServerError, String message) {
        this.message=message;
        this.details=internalServerError;
    }

    // Constructors, getters, and setters


}
