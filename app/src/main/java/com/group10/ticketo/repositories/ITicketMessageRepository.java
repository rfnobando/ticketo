package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketMessageRepository extends JpaRepository<TicketMessage,Long> {
}
