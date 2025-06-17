package com.group10.ticketo.services;

import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Status;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITicketStatusService {
     String findByTicketIdOrderByCreatedAtDesc(Long ticketId);
     TicketStatus findLastTicketStatusFromTicket(Long ticketId);
     TicketStatus createTicketStatus(Long ticketId, String statusName, Long employeeId) throws Exception;
     TicketStatus finishTicketStatus(Long ticketStatusId) throws Exception;
}

