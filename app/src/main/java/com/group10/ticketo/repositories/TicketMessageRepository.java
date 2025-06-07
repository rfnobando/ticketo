package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketMessageRepository extends JpaRepository<TicketMessage,Long> {

    //Trae todos los mensajes por ID de ticket
    public abstract List<TicketMessage> findByTicketId(Long ticketId);

    //Traer mensajes por ticket y ordenados por fecha
    public abstract List<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);

    //Filtrar por ticket y persona
    public abstract List<TicketMessage> findByTicketIdAndPersonId(Long ticketId, Long personId);

    //Ultimo mensaje de un ticket
    public abstract Optional<TicketMessage> findFirstByTicketIdOrderByCreatedAtDesc(Long ticketId);
}
