package com.proceed.swhackathon.exception.s3;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class EmptyFileException extends SwhackathonException {
    public EmptyFileException() {
        super(HttpStatus.BAD_REQUEST, Message.EMPTY_FILE);
    }
}
