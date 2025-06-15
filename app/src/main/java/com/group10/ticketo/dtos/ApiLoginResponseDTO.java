package com.group10.ticketo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ApiLoginResponseDTO(
        @NotBlank
        String message,
        @NotBlank
        @JsonProperty("status_code")
        int statusCode
) { }
