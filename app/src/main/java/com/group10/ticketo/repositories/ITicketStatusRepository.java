package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketStatusRepository extends JpaRepository<TicketStatus, Long> {
}