package com.proceed.swhackathon.exception;

import org.springframework.http.HttpStatus;

public class SwhackathonException extends RuntimeException{

    private final HttpStatus httpStatus;

    public SwhackathonException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
