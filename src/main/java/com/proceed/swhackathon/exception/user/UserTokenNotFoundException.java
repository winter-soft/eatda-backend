package com.proceed.swhackathon.exception.user;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class UserTokenNotFoundException extends SwhackathonException {
    public UserTokenNotFoundException() {
        super(HttpStatus.UNAUTHORIZED, Message.USER_TOKEN_NOT_FOUND);
    }
}
