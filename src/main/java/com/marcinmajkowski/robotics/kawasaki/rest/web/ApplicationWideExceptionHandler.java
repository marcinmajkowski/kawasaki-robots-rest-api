package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.ErrorInfo;
import com.marcinmajkowski.robotics.kawasaki.rest.service.ResourceNotFoundException;
import com.marcinmajkowski.robotics.kawasaki.rest.service.UnknownRobotResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationWideExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    ErrorInfo handleResourceNotFound(HttpServletRequest request, Exception exception) {
        return new ErrorInfo(request.getRequestURL().toString(), exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnknownRobotResponseException.class)
    @ResponseBody
    ErrorInfo handleUnknownRobotResponse(HttpServletRequest request, Exception exception) {
        return new ErrorInfo(request.getRequestURL().toString(), exception);
    }
}
