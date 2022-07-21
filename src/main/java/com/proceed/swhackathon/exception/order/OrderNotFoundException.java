package com.proceed.swhackathon.exception.order;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends SwhackathonException {
    public OrderNotFoundException() {
        super(HttpStatus.BAD_REQUEST, Message.ORDER_NOT_FOUND);
    }
}
