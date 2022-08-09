package com.proceed.swhackathon.exception;

import com.proceed.swhackathon.dto.ExceptionDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.MethodNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SwhackathonException.class)
    public ResponseDTO<ExceptionDTO> swHackathonException(SwhackathonException e){
        return new ResponseDTO<>(e.getHttpStatus().value(), new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseDTO<ExceptionDTO> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return new ResponseDTO<>(HttpStatus.METHOD_NOT_ALLOWED.value(), new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseDTO<ExceptionDTO> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), new ExceptionDTO(e.getMessage()));
    }
}
