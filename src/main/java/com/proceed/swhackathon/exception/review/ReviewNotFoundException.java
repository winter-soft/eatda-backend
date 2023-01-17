package com.proceed.swhackathon.exception.review;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends SwhackathonException {
    public ReviewNotFoundException() {
        super(HttpStatus.NOT_FOUND, Message.USER_NOT_FOUND);
    }
}
