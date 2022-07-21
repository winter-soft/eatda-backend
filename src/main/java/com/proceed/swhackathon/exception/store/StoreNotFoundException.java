package com.proceed.swhackathon.exception.store;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class StoreNotFoundException extends SwhackathonException {

    public StoreNotFoundException() {
        super(HttpStatus.BAD_REQUEST, Message.STORE_NOT_FOUND);
    }
}
