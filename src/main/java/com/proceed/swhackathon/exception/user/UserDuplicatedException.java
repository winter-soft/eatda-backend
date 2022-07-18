package com.proceed.swhackathon.exception.user;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class UserDuplicatedException extends SwhackathonException {

    public UserDuplicatedException() {
        super(HttpStatus.BAD_REQUEST, Message.USER_DUPLICATED);
    }
}
