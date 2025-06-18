package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.Ticket;

import java.util.List;

public interface ITicketService {
    //Traer Lista de Tickets creados por un cliente
    List<TicketDTO> findByCustomerId(Long customerId);

    Ticket findByIdTicket(Long ticketId) throws Exception;

    List<TicketDTO> findByCustomerIdAndFilters(Long customerId,String state,String order);

    List<TicketDTO> findTicketsByDepartmentId(Long departmentId);

    List<TicketDTO> findTicketsByDepartmentIdAndFilters(Long departmentId,String state,String order);

    //Crear Ticket que viene del controller
    void createTicket(CreateTicketDTO createTicketDTO) throws Exception;

    //Crear DTO de ticket
    TicketDTO findById(Long ticketId) throws Exception;

    //Trae el id de la persona que creo ticket
    Long findCustomerId (Long ticketId) throws Exception;
    //Trae a los tickets respondidos por un empleado
    List<TicketDTO> findTicketsAnsweredByEmployee(Long employeeId);
    //Trae a los tickets respondidos por un empleado segun el filtro
    List<TicketDTO> findTicketsAnsweredByEmployeeAndFilters(Long employeeId,String state,String order);
}
