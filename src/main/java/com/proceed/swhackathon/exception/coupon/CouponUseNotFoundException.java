package com.proceed.swhackathon.exception.coupon;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class CouponUseNotFoundException extends SwhackathonException {
    public CouponUseNotFoundException() {
        super(HttpStatus.NOT_FOUND, Message.COUPONUSE_NOT_FOUND);
    }
}
