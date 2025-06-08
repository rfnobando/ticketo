package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface ITicketCategoryRepository extends JpaRepository <TicketCategory, Long> {
}
