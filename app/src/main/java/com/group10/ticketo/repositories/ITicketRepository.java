package com.group10.ticketo.repositories;
import com.group10.ticketo.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    //Trae los tickets de un cliente segun el id que le pases.
    public abstract List<Ticket> findByCustomerId(Long customerId);
}