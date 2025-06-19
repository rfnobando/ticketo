package com.group10.ticketo.dtos;

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

    @NotNull(message = "Debes seleccionar un departamento")
    private Long deparmentId;

    @Positive(message = "El monto debe ser mayor a 0")
    @NotNull(message = "Debes cargar un monto")
    private double amount;
}
