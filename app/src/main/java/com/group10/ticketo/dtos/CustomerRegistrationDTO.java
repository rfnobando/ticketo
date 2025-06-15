package com.group10.ticketo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRegistrationDTO {
    @NotBlank(message = "Debes ingresar al menos un nombre")
    private String name;

    @NotBlank(message = "Debes ingresar al menos un apellido")
    private String surname;

    @NotBlank(message = "Debes ingresar un teléfono")
    private String phoneNumber;

    @NotBlank(message = "Debes ingresar un email")
    @Email
    private String email;

    @NotBlank(message = "Debes ingresar una contraseña")
    private String password;

    @NotBlank(message = "Debes repetir la contraseña")
    private String confirmPassword;
}