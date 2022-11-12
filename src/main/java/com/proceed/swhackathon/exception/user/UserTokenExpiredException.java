package com.proceed.swhackathon.exception.user;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;

public class UserTokenExpiredException extends SwhackathonException {

    public UserTokenExpiredException() {
        super(HttpStatus.UNAUTHORIZED, Message.USER_TOKEN_EXPIRED);
    }
}
