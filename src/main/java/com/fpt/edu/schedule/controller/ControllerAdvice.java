package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerErrorException;

@org.springframework.web.bind.annotation.ControllerAdvice


public class ControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invalidRequest(InvalidRequestException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String serverErrorHandler(ServerErrorException ex) {
        return ex.getMessage();
    }
}
