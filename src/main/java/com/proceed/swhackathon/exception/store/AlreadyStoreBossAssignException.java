package com.proceed.swhackathon.exception.store;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class AlreadyStoreBossAssignException extends SwhackathonException {
    public AlreadyStoreBossAssignException() {
        super(HttpStatus.BAD_REQUEST, Message.ALREADY_STORE_BOSS_ASSIGN);
    }
}
