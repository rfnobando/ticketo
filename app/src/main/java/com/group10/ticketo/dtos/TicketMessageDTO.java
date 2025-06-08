package com.group10.ticketo.dtos;

import com.group10.ticketo.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketMessageDTO {
    private String body;
    private String pictureUrl;
    private LocalDateTime createdAt;
    private Long personId;
    private String personName;
}
