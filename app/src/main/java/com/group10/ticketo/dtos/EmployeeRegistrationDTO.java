package com.group10.ticketo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EmployeeRegistrationDTO {

    @NotBlank(message = "Debes ingresar al menos un nombre")
    private String name;

    @NotBlank(message = "Debes ingresar al menos un apellido")
    private String surname;

    @NotBlank(message = "Debes ingresar un email")
    @Email
    private String email;

    @NotBlank(message = "Debes ingresar una contraseña")
    private String password;

    @NotBlank(message = "Debes repetir la contraseña")
    private String confirmPassword;

    @NotNull(message = "Debes seleccionar un departamento")
    private Long deparmentId;

    @Positive(message = "El monto debe ser mayor a 0")
    @NotNull(message = "Debes cargar un monto")
    private double amount;
}
