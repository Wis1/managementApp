package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with given uuid doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsNotAdministrator.class)
    public ResponseEntity<Object> handleProductNotFoundException(UserIsNotAdministrator exception) {
        return new ResponseEntity<>("User is not administrator", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsAlreadyExists.class)
    public ResponseEntity<Object> handleProductNotFoundException(UserIsAlreadyExists exception) {
        return new ResponseEntity<>("User is already exist", HttpStatus.BAD_REQUEST);
    }
}
