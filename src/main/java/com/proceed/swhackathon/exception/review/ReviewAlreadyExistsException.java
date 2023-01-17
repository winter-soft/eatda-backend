package com.proceed.swhackathon.exception.review;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class ReviewAlreadyExistsException extends SwhackathonException {
    public ReviewAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, Message.REVIEW_ALREADY_EXISTS);
    }
}
