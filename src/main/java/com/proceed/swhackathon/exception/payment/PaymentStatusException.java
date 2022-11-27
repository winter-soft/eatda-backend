package com.proceed.swhackathon.exception.payment;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class PaymentStatusException extends SwhackathonException {
    public PaymentStatusException() {
        super(HttpStatus.BAD_REQUEST, Message.PAYMENT_STATUS);
    }
}
