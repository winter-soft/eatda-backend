package com.proceed.swhackathon.exception.payment;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class PaymentAmountNotMachException extends SwhackathonException {
    public PaymentAmountNotMachException() {
        super(HttpStatus.BAD_REQUEST, Message.PAYMENT_AMOUNT_NOT_MATCH);
    }
}
