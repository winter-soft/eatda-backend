package com.proceed.swhackathon.exception;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends SwhackathonException{

    public IllegalArgumentException() {
        super(HttpStatus.BAD_REQUEST,Message.ILLEGAL_ARGUMENT);
    }
}
