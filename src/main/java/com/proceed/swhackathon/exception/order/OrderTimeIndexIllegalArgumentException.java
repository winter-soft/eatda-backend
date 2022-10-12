package com.proceed.swhackathon.exception.order;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class OrderTimeIndexIllegalArgumentException extends SwhackathonException {
    public OrderTimeIndexIllegalArgumentException() {
        super(HttpStatus.BAD_REQUEST, Message.ORDER_TIMEINDEX_ILLEGAL);
    }
}
