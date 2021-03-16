package com.example.shopping.productservice.exception.dto;

public class ErrorUtilFieldDto {

    private final String objectName;

    private final String field;

    private final String message;

    public ErrorUtilFieldDto(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
