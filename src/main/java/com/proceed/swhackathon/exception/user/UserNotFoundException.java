package com.proceed.swhackathon.exception.user;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends SwhackathonException {
    public UserNotFoundException() {
        super(HttpStatus.BAD_REQUEST, Message.USER_NOT_FOUND);
    }
}
