package com.vincennlin.collofulbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceRelationException extends RuntimeException {

    private final String parentResource;
    private final Long parentResourceId;
    private final String childResource;
    private final Long childResourceId;

    public ResourceRelationException(String parentResource, Long parentResourceId,
                                     String childResource, Long childResourceId) {
        super(String.format("Cannot access child resource '%s' with id: '%d' " +
                "because it is not related to parent resource '%s' with id: '%d'",
                childResource, childResourceId, parentResource, parentResourceId));
        this.parentResource = parentResource;
        this.parentResourceId = parentResourceId;
        this.childResource = childResource;
        this.childResourceId = childResourceId;
    }
}
