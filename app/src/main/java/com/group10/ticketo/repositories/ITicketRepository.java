package com.group10.ticketo.repositories;
import com.group10.ticketo.entities.Ticket;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    //Trae los tickets de un cliente segun el id que le pases.
    List<Ticket> findByCustomerId(Long customerId);

    @Query("SELECT t FROM Ticket t JOIN t.ticketCategory c JOIN c.departments d WHERE d.id = :departmentId")
    List<Ticket> findTicketsByDepartmentId(@Param("departmentId") Long departmentId);
}