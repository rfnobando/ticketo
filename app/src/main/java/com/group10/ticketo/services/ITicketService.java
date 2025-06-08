package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CreateTicketDTO;
<<<<<<< HEAD
import com.group10.ticketo.dtos.TicketDTO;
=======
>>>>>>> POD-39
import com.group10.ticketo.entities.Customer;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.TicketCategory;

<<<<<<< HEAD
import com.group10.ticketo.entities.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITicketService {
    //Traer Lista de Tickets creados por un cliente
    List<TicketDTO> findByCustomerId(Long customerId);

    //Traer Lista de Tickets de un Departamento
    List<Ticket> findTicketsByDepartmentId(Long departmentId);

    //Crear Ticket que viene del controller
    void createTicket(CreateTicketDTO createTicketDTO) throws Exception;

    //Crear DTO de ticket
    TicketDTO findById(Long ticketId) throws Exception;

    //Trae el id de la persona que creo ticket
    Long findCustomerId (Long ticketId) throws Exception;
=======
public interface ITicketService {
    void createTicket(CreateTicketDTO createTicketDTO) throws Exception;
>>>>>>> POD-39
}
