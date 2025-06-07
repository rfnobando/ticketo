package com.group10.ticketo.services;

import com.group10.ticketo.entities.Ticket;

import java.util.List;

public interface ITicketService {
    //Traer Lista de Tickets creados por un cliente
    public List<Ticket> findByCustomerId(Long customerId);
}
