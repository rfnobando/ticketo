package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService {

    @Autowired
    private ITicketRepository ticketRepository;

    @Override
    public List<Ticket> findByCustomerId(Long customerId){
        return ticketRepository.findByCustomerId(customerId);
    }
    @Override
    public List<Ticket> findTicketsByDepartmentId(Long departmentId){
        return ticketRepository.findTicketsByDepartmentId(departmentId);
    }
}
