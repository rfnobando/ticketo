package com.group10.ticketo.dtos;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateTicketDTO {

    @NotEmpty(message = "El título no puede estar vacío.")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres.")
    private String title;

    //  @NotNull(message = "Debe seleccionar un cliente.")
    private Long customerId;

    @NotNull(message = "Debe seleccionar una categoría.")
    private Long ticketCategoryId;

    @NotEmpty(message = "El mensaje no puede estar vacío.")
    @Size(max = 1000, message = "El mensaje no puede superar los 1000 caracteres.")
    private String messageBody;

    @Size(max = 255, message = "La URL de la imagen es demasiado larga.")
    private String pictureUrl;

}
