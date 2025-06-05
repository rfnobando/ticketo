package com.group10.ticketo.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    //private Cliente cliente;
    //private Categoria categoriaTicket;
    //private List<TicketEstado> estados;
    //private List<MensajeTicket> mensajes;
}