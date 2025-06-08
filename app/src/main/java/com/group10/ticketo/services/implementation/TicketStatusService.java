package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Status;
import com.group10.ticketo.entities.Ticket;

import com.group10.ticketo.entities.TicketStatus;
import com.group10.ticketo.repositories.IEmployeeRepository;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.repositories.ITicketStatusRepository;
import com.group10.ticketo.services.IStatusService;
import com.group10.ticketo.services.ITicketStatusService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class TicketStatusService implements ITicketStatusService {

    @Autowired
    private ITicketStatusRepository ticketStatusRepository;
    @Autowired
    private IStatusService statusService;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public TicketStatus createTicketStatus(Long ticketId, String statusName, Long employeeId) throws Exception {
        if (ticketId == null || statusName == null) {
            throw new Exception("ERROR: Required data missing to create Ticket Status.");
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new Exception("ERROR: Ticket not found"));
        Employee employee = null;
        if (employeeId != null){
            employee = employeeRepository.findById(employeeId).orElseThrow(()-> new Exception("ERROR:Employee not found"));
        }

        TicketStatus ticketStatus = new TicketStatus();
        ticketStatus.setTicket(ticket);
        ticketStatus.setStatus(statusService.findByName(statusName));
        ticketStatus.setEmployee(employee);

        ticketStatusRepository.save(ticketStatus);
        return ticketStatus;

    }
}