package com.vincennlin.collofulbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public WebAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
