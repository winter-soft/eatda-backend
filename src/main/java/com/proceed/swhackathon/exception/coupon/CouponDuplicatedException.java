package com.proceed.swhackathon.exception.coupon;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class CouponDuplicatedException extends SwhackathonException {
    public CouponDuplicatedException() {
        super(HttpStatus.BAD_REQUEST, Message.COUPON_DUPLICATED);
    }
}
