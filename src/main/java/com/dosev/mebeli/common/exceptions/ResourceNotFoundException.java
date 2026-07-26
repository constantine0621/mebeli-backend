package com.dosev.mebeli.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entityName, Object id) {
        super(entityName + " not found with id: " + id);
    }
}
