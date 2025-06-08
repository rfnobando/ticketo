package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketMessage;
import com.group10.ticketo.repositories.ITicketMessageRepository;
import com.group10.ticketo.services.ITicketMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketMessageService implements ITicketMessageService {


    private final ITicketMessageRepository ticketMessageRepository;

    public TicketMessageService(ITicketMessageRepository ticketMessageRepository){
        this.ticketMessageRepository = ticketMessageRepository;
    }

    @Override
    //Trae todos los mensajes por ID de ticket
    public List<TicketMessage> findByTicketId(Long ticketId){
        return ticketMessageRepository.findByTicketId(ticketId);
    }
    @Override
    //Traer mensajes por ticket y ordenados por fecha
    public List<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId){
        return ticketMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    @Override
    //Filtrar por ticket y persona
    public List<TicketMessage> findByTicketIdAndPersonId(Long ticketId, Long personId){
        return ticketMessageRepository.findByTicketIdAndPersonId(ticketId,personId);
    }

    @Override
    //Ultimo mensaje de un ticket
    public Optional<TicketMessage> findFirstByTicketIdOrderByCreatedAtDesc(Long ticketId){
        return ticketMessageRepository.findFirstByTicketIdOrderByCreatedAtDesc(ticketId);
    }
    @Override
    public List<Ticket> findTicketsByEmployeeId(Long employeeId){
        return ticketMessageRepository.findTicketsByEmployeeId(employeeId);
    }
}
