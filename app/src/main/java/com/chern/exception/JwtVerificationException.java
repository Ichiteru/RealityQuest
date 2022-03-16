package com.chern.exception;

public class JwtVerificationException extends RuntimeException{
    public JwtVerificationException(String message) {
        super(message);
    }
}
