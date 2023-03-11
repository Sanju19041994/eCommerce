package com.shruteekatech.eCommerce.exception;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;

    public ResourceNotFoundException(String resourceName, String fieldName) {
        super(String.format("%s not found with given %s", resourceName, fieldName));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
    }

}
