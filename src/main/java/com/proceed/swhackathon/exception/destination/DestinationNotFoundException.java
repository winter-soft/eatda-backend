package com.proceed.swhackathon.exception.destination;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class DestinationNotFoundException extends SwhackathonException {
    public DestinationNotFoundException() {
        super(HttpStatus.BAD_REQUEST, Message.DESTINATION_NOT_FOUND);
    }
}
