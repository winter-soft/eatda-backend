package com.proceed.swhackathon.exception.menu;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class MenuNotMatchingStoreException extends SwhackathonException {
    public MenuNotMatchingStoreException() {
        super(HttpStatus.BAD_REQUEST, Message.MENU_NOT_MATCHING);
    }
}
