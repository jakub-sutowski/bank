package com.example.bank.exception;

import com.example.bank.exception.exceptions.UserAlreadyExist;
import com.example.bank.exception.exceptions.UserNotExist;
import com.example.bank.exception.exceptions.ValidationRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
@Log4j2
public class ApiExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExist.class})
    public ResponseEntity<Object> handlerUserAlreadyExist(UserAlreadyExist exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        log.error("UserAlreadyExist: {}", exception.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {UserNotExist.class})
    public ResponseEntity<Object> handlerUserNotExist(UserNotExist exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        log.error("UserNotExist: {}", exception.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ValidationRequestException.class})
    public ResponseEntity<Object> handlerValidationRequestException(ValidationRequestException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        log.error("ValidationRequestException: {}", exception.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
