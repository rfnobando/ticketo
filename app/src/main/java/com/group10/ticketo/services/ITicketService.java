package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.Customer;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.TicketCategory;

import com.group10.ticketo.entities.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITicketService {
    //Traer Lista de Tickets creados por un cliente
    List<TicketDTO> findByCustomerId(Long customerId);

    List<TicketDTO> findTicketsByDepartmentId(Long departmentId);

    //Crear Ticket que viene del controller
    void createTicket(CreateTicketDTO createTicketDTO) throws Exception;

    //Crear DTO de ticket
    TicketDTO findById(Long ticketId) throws Exception;

    //Trae el id de la persona que creo ticket
    Long findCustomerId (Long ticketId) throws Exception;
}
