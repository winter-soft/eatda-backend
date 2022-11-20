package com.proceed.swhackathon.exception.menuOption;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class MenuOptionNotFoundException extends SwhackathonException {
    public MenuOptionNotFoundException() {
        super(HttpStatus.NOT_FOUND, Message.MENU_OPTION_NOT_FOUND);
    }
}
