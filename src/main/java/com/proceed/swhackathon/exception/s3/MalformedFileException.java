package com.proceed.swhackathon.exception.s3;

import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class MalformedFileException extends SwhackathonException {
    public MalformedFileException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
