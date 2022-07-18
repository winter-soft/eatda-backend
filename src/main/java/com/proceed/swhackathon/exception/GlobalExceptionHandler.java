package com.proceed.swhackathon.exception;

import com.proceed.swhackathon.dto.ExceptionDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SwhackathonException.class)
    public ResponseDTO<ExceptionDTO> swHackathonException(SwhackathonException e){
        return new ResponseDTO<>(e.getHttpStatus().value(), new ExceptionDTO(e.getMessage()));
    }

}
