package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.entities.Customer;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.TicketCategory;

import com.group10.ticketo.entities.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITicketService {
    //Traer Lista de Tickets creados por un cliente
    public List<Ticket> findByCustomerId(Long customerId);

    //Traer Lista de Tickets de un Departamento
    public List<Ticket> findTicketsByDepartmentId(Long departmentId);

    //Crear Ticket que viene del controller
    void createTicket(CreateTicketDTO createTicketDTO) throws Exception;
}
