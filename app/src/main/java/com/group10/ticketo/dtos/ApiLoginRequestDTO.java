package com.group10.ticketo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ApiLoginRequestDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) { }
