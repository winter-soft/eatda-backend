package com.proceed.swhackathon.exception.user;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class UserUnAuthorizedException extends SwhackathonException {

    public UserUnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED, Message.USER_UNAUTHORIZED);
    }
}
