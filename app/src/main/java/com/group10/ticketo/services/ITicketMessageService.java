package com.group10.ticketo.services;

import com.group10.ticketo.entities.TicketMessage;

import java.util.List;
import java.util.Optional;

public interface ITicketMessageService {
    //Trae todos los mensajes por ID de ticket
    public List<TicketMessage> findByTicketId(Long ticketId);

    //Traer mensajes por ticket y ordenados por fecha
    public List<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);

    //Filtrar por ticket y persona
    public List<TicketMessage> findByTicketIdAndPersonId(Long ticketId, Long personId);

    //Ultimo mensaje de un ticket
    public Optional<TicketMessage> findFirstByTicketIdOrderByCreatedAtDesc(Long ticketId);

}
