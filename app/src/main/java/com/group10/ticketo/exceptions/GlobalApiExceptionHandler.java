package com.group10.ticketo.exceptions;

import com.group10.ticketo.dtos.ApiLoginResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(ApiInvalidCredentialsException.class)
    public ResponseEntity<ApiLoginResponseDTO> handleApiInvalidCredentialsException(ApiInvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiLoginResponseDTO(ex.getMessage(), 401));
    }

}
