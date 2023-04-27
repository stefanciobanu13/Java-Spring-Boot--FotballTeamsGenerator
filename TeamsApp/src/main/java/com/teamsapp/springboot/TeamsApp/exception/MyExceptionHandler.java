package com.teamsapp.springboot.TeamsApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody

    public ErrorResponse handleMyException(MyException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}