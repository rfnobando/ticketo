package com.group10.ticketo.services.implementation;



import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.entities.*;
import com.group10.ticketo.repositories.*;
import com.group10.ticketo.services.ICustomerService;
import com.group10.ticketo.services.IStatusService;
import com.group10.ticketo.services.ITicketService;
import com.group10.ticketo.services.ITicketStatusService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service

public class TicketService implements ITicketService {
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private IStatusService statusService;
    @Autowired
    private ITicketStatusService ticketStatusService;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private ITicketCategoryRepository ticketCategoryRepository;
    @Autowired
    private ITicketMessageRepository ticketMessageRepository;

    @Transactional
    public void createTicket(CreateTicketDTO createTicketDTO) throws Exception{
        if (createTicketDTO.getTitle()== null || createTicketDTO.getTitle().isEmpty() || createTicketDTO.getCustomerId() == null || createTicketDTO.getTicketCategoryId() == null ) {
            throw new Exception("ERROR: Some required ticket fields are missing.");
        }
        Customer customer = customerRepository.findById(createTicketDTO.getCustomerId())
                .orElseThrow(() -> new Exception("ERROR:Customer not found."));

        TicketCategory ticketCategory = ticketCategoryRepository.findById(createTicketDTO.getTicketCategoryId())
                .orElseThrow(() -> new Exception("ERROR:Ticket Category not found."));

        Ticket ticket = new Ticket();
        ticket.setTitle(createTicketDTO.getTitle());
        ticket.setCustomer(customer);
        ticket.setTicketCategory(ticketCategory);

        ticketRepository.save(ticket);

        ticketStatusService.createTicketStatus(ticket.getId(),"PENDING",null);


        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setTicket(ticket);
        ticketMessage.setBody(createTicketDTO.getMessageBody());
        ticketMessage.setPictureUrl(createTicketDTO.getPictureUrl());
        ticketMessage.setPerson(customer);

        ticketMessageRepository.save(ticketMessage);
    }
}
