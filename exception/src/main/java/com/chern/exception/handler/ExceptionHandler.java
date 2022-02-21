package com.chern.exception.handler;

import com.chern.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ApiError> handleNoSuchDataException(Exception e, WebRequest request){
        ApiError apiError = ApiErrorBuilder.anApiError()
                .withTimestamp(LocalDateTime.now())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(e.getMessage()).build();
//        ApiError apiError = new ApiError(ex.getMessage());
        ResponseEntity<ApiError> responseEntity = new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectPathVariableException.class)
    public ResponseEntity<ApiError> handleIncorrectPathVariableException(Exception e, WebRequest request){
        ApiError apiError = ApiErrorBuilder.anApiError()
                .withTimestamp(LocalDateTime.now())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(e.getMessage()).build();
        ResponseEntity<ApiError> responseEntity = new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ApiError> handleIncorrectDataException(Exception e, WebRequest request){
        ApiError apiError = ApiErrorBuilder.anApiError()
                .withTimestamp(LocalDateTime.now())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(e.getMessage()).build();
        ResponseEntity<ApiError> responseEntity = new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = ApiErrorBuilder.anApiError().withError("asd")
                .withStatus(HttpStatus.NOT_FOUND.value()).withTimestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
//        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }
}
