package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundException exception) {
        return exception.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(UserIsNotAdministrator.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String userIsNotAdministratorHandler(UserIsNotAdministrator exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserIsAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userIsAlreadyExistsHandler(UserIsAlreadyExists exception) {
        return exception.getMessage();
    }
}
