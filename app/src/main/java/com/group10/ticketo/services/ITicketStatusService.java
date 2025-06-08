package com.group10.ticketo.services;

import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Status;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketStatus;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITicketStatusService {
    public String findByTicketIdOrderByCreatedAtDesc(Long ticketId);
    public TicketStatus createTicketStatus(Long ticketId, String statusName, Long employeeId) throws Exception;
=======

public interface ITicketStatusService {
     TicketStatus createTicketStatus(Long ticketId, String statusName, Long employeeId) throws Exception;
>>>>>>> POD-39
}

