package com.example.shopping.productservice.exception;

import com.example.shopping.productservice.exception.dto.ErrorUtilDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorUtil {

    public static ResponseEntity<ErrorUtilDto> create(HttpStatus status, String message, String description) {
        return new ResponseEntity<>(new ErrorUtilDto(message, description), status);
    }

    public static ResponseEntity<ErrorUtilDto> create(HttpStatus status, String message) {
        return create(status, message, null);
    }

    public static ResponseEntity<ErrorUtilDto> create(String message, String description) {
        return create(HttpStatus.BAD_REQUEST, message, description);
    }

    public static ResponseEntity<ErrorUtilDto> create(String message) {
        return create(message, null);
    }
}
