package com.vincennlin.collofulbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceOwnershipException extends RuntimeException {

    private Long userId;
    private Long ownerId;

    public ResourceOwnershipException(Long userId, Long ownerId) {
        super(String.format("Current user with id: '%d' cannot access resource owned by user with id: '%s'", userId, ownerId));
        this.userId = userId;
        this.ownerId = ownerId;
    }
}
