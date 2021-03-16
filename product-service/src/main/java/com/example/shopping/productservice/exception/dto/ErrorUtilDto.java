package com.example.shopping.productservice.exception.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtilDto {

    private final String message;
    private final String description;

    private List<ErrorUtilFieldDto> fieldErrors;

    public ErrorUtilDto(String message) {
        this(message, null);
    }

    public ErrorUtilDto(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public ErrorUtilDto(String message, String description, List<ErrorUtilFieldDto> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new ErrorUtilFieldDto(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<ErrorUtilFieldDto> getFieldErrors() {
        return fieldErrors;
    }
}
