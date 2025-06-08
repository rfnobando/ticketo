package com.group10.ticketo.dtos;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateTicketDTO {

    private String title;
    private Long customerId;
    private Long ticketCategoryId;
    private String messageBody;
    private String pictureUrl;
}
