package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketStatus;
import com.group10.ticketo.repositories.IEmployeeRepository;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.repositories.ITicketStatusRepository;
import com.group10.ticketo.services.IStatusService;
import com.group10.ticketo.services.ITicketStatusService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketStatusService implements ITicketStatusService {

    private final ITicketStatusRepository ticketStatusRepository;
    private final IStatusService statusService;
    private final ITicketRepository ticketRepository;
    private final IEmployeeRepository employeeRepository;
    public TicketStatusService(ITicketStatusRepository ticketStatusRepository, IStatusService statusService,
                               ITicketRepository ticketRepository, IEmployeeRepository employeeRepository){
        this.ticketStatusRepository = ticketStatusRepository;
        this.statusService = statusService;
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
    }
    @Override
    public String findByTicketIdOrderByCreatedAtDesc(Long ticketId){
        Pageable limitOne = PageRequest.of(0, 1);
        List<TicketStatus> result = ticketStatusRepository.findByTicketIdOrderByCreatedAtDesc(ticketId, limitOne);
        return result.isEmpty() ? null : result.get(0).getStatus().getName();
    }

    @Override
    public TicketStatus createTicketStatus(Long ticketId, String statusName, Long employeeId) throws Exception {
        if (ticketId == null || statusName == null) {
            throw new Exception("ERROR: Required data missing to create Ticket Status.");
        }

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new Exception("ERROR: Ticket not found"));
        Employee employee = null;

        if (employeeId != null) {
            employee = employeeRepository.findById(employeeId).orElseThrow(() -> new Exception("ERROR:Employee not found"));
        }

        TicketStatus ticketStatus = new TicketStatus();
        ticketStatus.setTicket(ticket);
        ticketStatus.setStatus(statusService.findByName(statusName));
        ticketStatus.setEmployee(employee);

        ticketStatusRepository.save(ticketStatus);
        return ticketStatus;

    }

}

