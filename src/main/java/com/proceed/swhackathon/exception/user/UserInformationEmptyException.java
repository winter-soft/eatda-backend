package com.proceed.swhackathon.exception.user;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class UserInformationEmptyException extends SwhackathonException {
    public UserInformationEmptyException() {
        super(HttpStatus.BAD_REQUEST, Message.USER_INFORMATION_EMPTY);
    }
}
