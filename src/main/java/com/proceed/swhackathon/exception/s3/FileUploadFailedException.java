package com.proceed.swhackathon.exception.s3;

import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.SwhackathonException;
import org.springframework.http.HttpStatus;

public class FileUploadFailedException extends SwhackathonException {
    public FileUploadFailedException() {
        super(HttpStatus.BAD_REQUEST, Message.FILE_UPLOAD_FAILED);
    }
}
