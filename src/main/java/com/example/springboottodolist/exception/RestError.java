package com.example.springboottodolist.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class RestError implements Serializable {
    private final HttpStatus status;
    private final String error;

    public RestError(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
