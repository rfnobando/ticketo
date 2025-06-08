package com.group10.ticketo.services;

import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Status;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketStatus;

public interface ITicketStatusService {
     TicketStatus createTicketStatus(Long ticketId, String statusName, Long employeeId) throws Exception;
}

