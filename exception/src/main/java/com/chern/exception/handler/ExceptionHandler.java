package com.chern.exception.handler;

import com.chern.exception.NoSuchDataException;
import com.chern.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ApiError> handleNoSuchDataException(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//        ApiError apiError = new ApiError(ex.getMessage());
        ResponseEntity<ApiError> apiErrorResponseEntity = new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        return apiErrorResponseEntity;
    }


}
