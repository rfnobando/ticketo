package com.group10.ticketo.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketMessageDTO {
    @NotEmpty(message = "El mensaje no puede estar vacío.")
    @Size(max = 1000, message = "El mensaje no puede superar los 1000 caracteres.")
    private String body;

    @Size(max = 255, message = "La URL de la imagen es demasiado larga.")
    private String pictureUrl;

    private Long personId;

    private Long ticketId;


}
