package com.group10.ticketo.repositories;
import com.group10.ticketo.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

@Repository("ticketRepository")
public interface ITicketRepository extends JpaRepository<Ticket, Serializable> {
}