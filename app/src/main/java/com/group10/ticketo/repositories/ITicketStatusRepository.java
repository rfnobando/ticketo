package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.TicketStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketStatusRepository extends JpaRepository<TicketStatus, Long> {
    List<TicketStatus> findByTicketIdOrderByCreatedAtDesc(Long ticketId, Pageable pageable);
}
