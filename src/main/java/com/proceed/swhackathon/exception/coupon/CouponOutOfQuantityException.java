package com.proceed.swhackathon.exception.coupon;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class CouponOutOfQuantityException extends SwhackathonException {
    public CouponOutOfQuantityException() {
        super(HttpStatus.BAD_REQUEST, Message.COUPON_OUTOFQUANTITY);
    }
}
