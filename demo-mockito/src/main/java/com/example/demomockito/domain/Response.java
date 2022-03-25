package com.example.demomockito.domain;

import lombok.Data;

@Data
public final class Response<T> {
    private String returnType;
    private String returnCode;
    private String message;

    private T body;

    public Response(String returnType, String returnCode, String message) {
        this.returnType = returnType;
        this.returnCode = returnCode;
        this.message = message;
    }

    public Response(String returnType, String returnCode, String message, T body) {
        this.returnType = returnType;
        this.returnCode = returnCode;
        this.message = message;
        this.body = body;
    }

}
