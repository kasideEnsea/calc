package com.example.calc.exception;

import org.springframework.http.HttpStatus;

public class WebException extends RuntimeException {
    private final HttpStatus httpStatus;

    public WebException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
