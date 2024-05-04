package com.zuzex.storageservice.handler;

import com.zuzex.storageservice.exception.ErrorMessage;
import com.zuzex.storageservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleResourceNotFound(ResourceNotFoundException exception) {
        log.error("Product was not found in the database");
        return new ErrorMessage(exception.getMessage());
    }

}
