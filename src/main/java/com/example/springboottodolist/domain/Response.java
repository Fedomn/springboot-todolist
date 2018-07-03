package com.example.springboottodolist.domain;

import org.springframework.http.HttpStatus;

public class Response<T> {
    private T Data;

    private String message;

    private HttpStatus status;

    public Response(T data, String message, HttpStatus status) {
        Data = data;
        this.message = message;
        this.status = status;
    }

    public Response(T data, HttpStatus status) {
        Data = data;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return Data;
    }
}
