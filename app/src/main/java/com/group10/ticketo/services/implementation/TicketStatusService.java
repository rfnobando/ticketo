package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.TicketStatus;
import com.group10.ticketo.repositories.ITicketStatusRepository;
import com.group10.ticketo.services.ITicketStatusService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketStatusService implements ITicketStatusService {

    private final ITicketStatusRepository ticketStatusRepository;

    public TicketStatusService(ITicketStatusRepository ticketStatusRepository){
        this.ticketStatusRepository = ticketStatusRepository;
    }

    public String findByTicketIdOrderByCreatedAtDesc(Long ticketId){
        Pageable limitOne = PageRequest.of(0, 1);
        List<TicketStatus> result = ticketStatusRepository.findByTicketIdOrderByCreatedAtDesc(ticketId, limitOne);
        return result.isEmpty() ? null : result.get(0).getStatus().getName();
    }
}
