package com.group10.ticketo.services;

import com.group10.ticketo.entities.TicketStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITicketStatusService {
    public String findByTicketIdOrderByCreatedAtDesc(Long ticketId);
}
