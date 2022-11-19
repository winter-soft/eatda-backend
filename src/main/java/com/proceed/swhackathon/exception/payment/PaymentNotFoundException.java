package com.proceed.swhackathon.exception.payment;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class PaymentNotFoundException extends SwhackathonException {
    public PaymentNotFoundException() {
        super(HttpStatus.BAD_REQUEST, Message.PAYMENT_NOT_FOUND);
    }
}
