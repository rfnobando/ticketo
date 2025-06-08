package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.entities.Customer;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.TicketCategory;

public interface ITicketService {
    void createTicket(CreateTicketDTO createTicketDTO) throws Exception;
}
