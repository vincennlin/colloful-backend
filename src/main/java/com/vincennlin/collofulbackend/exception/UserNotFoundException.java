package com.vincennlin.collofulbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserNotFoundException extends RuntimeException {

    private Long userId;

    public UserNotFoundException(Long userId) {
        super(String.format("User with id: '%d' not found", userId));
        this.userId = userId;
    }
}