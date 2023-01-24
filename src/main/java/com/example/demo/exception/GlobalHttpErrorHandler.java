package com.example.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

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

    @ResponseBody
    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String projectIsNotFoundExceptionHandler(ProjectNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserIsAlreadyInProject.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userIsAlreadyInProjectHandler(UserIsAlreadyInProject exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TimesheetIsAlreadyExistInThisTimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String timesheetIsAlreadyExistInThisTimeExceptionHandler(TimesheetIsAlreadyExistInThisTimeException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserOrProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userOrProjectNotFoundExceptionHandler(UserOrProjectNotFoundException exception) {
        return exception.getMessage();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> String.format("%s: %s", x.getField(), x.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
