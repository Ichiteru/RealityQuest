package com.chern.exception;

public class DuplicateFieldException extends RuntimeException{
    public DuplicateFieldException(String message) {
        super(message);
    }
}
