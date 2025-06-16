package com.group10.ticketo.exceptions;

public class ApiInvalidCredentialsException extends RuntimeException{
    public ApiInvalidCredentialsException(String message) {
        super(message);
    }
}
