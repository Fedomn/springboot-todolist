package com.example.springboottodolist.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response<T> {
    private T Data;

    private String[] Errors;

    public Response(T data) {
        Data = data;
    }

    public Response(String[] errors) {
        Errors = errors;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String[] getErrors() {
        return Errors;
    }

    public void setErrors(String[] errors) {
        Errors = errors;
    }
}
