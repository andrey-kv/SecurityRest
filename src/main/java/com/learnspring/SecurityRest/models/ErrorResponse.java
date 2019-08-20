package com.learnspring.SecurityRest.models;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(int status, Throwable ex) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        this.timestamp = formatter.format(date);

        this.status = status;
        this.error = ex.getClass().getName();
        this.message = ex.getMessage();
    }
}
