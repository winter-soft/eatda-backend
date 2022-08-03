package com.proceed.swhackathon.exception.userOrderDetail;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class UserOrderDetailNotFoundException extends SwhackathonException {
    public UserOrderDetailNotFoundException() {
        super(HttpStatus.BAD_REQUEST, Message.USER_ORDER_DETAIL_NOT_FOUND);
    }
}
