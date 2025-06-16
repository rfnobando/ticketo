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

    List<Ticket> findByCustomerIdOrderByCreatedAtAsc(Long customerId);

    List<Ticket> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    @Query("""
            SELECT t FROM Ticket t
            JOIN TicketStatus ts ON ts.ticket = t
            JOIN Status s ON ts.status = s
            WHERE t.customer.id = :customerId
              AND ts.createdAt = (
                 SELECT MAX(ts2.createdAt)
                 FROM TicketStatus ts2
                 WHERE ts2.ticket = t
              )
              AND s.name = :status
            ORDER BY t.createdAt ASC
            """)
    List<Ticket> findByCustomerIdAndStatusOrderByCreatedAtAsc(@Param("customerId") Long customerId,
                                                              @Param("status") String status);

    @Query("""
            SELECT t FROM Ticket t
            JOIN TicketStatus ts ON ts.ticket = t
            JOIN Status s ON ts.status = s
            WHERE t.customer.id = :customerId
              AND ts.createdAt = (
                 SELECT MAX(ts2.createdAt)
                 FROM TicketStatus ts2
                 WHERE ts2.ticket = t
              )
              AND s.name = :status
            ORDER BY t.createdAt DESC
            """)
    List<Ticket> findByCustomerIdAndStatusOrderByCreatedAtDesc(@Param("customerId") Long customerId,
                                                               @Param("status") String status);


    @Query("SELECT t FROM Ticket t JOIN t.ticketCategory c JOIN c.departments d WHERE d.id = :departmentId")
    List<Ticket> findTicketsByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("""
            SELECT t FROM Ticket t
            JOIN t.ticketCategory c
            JOIN c.departments d
            WHERE d.id = :departmentId
            ORDER BY t.createdAt ASC
            """)
    List<Ticket> findByDepartmentIdOrderByCreatedAtAsc(@Param("departmentId") Long departmentId);
    @Query("""
            SELECT t FROM Ticket t
            JOIN t.ticketCategory c
            JOIN c.departments d
            WHERE d.id = :departmentId
            ORDER BY t.createdAt DESC
            """)
    List<Ticket> findByDepartmentIdOrderByCreatedAtDesc(@Param("departmentId") Long departmentId);

    @Query("""
            SELECT t FROM Ticket t
            JOIN t.ticketCategory c
            JOIN c.departments d
            JOIN TicketStatus ts ON ts.ticket = t
            JOIN Status s ON ts.status = s
            WHERE d.id = :departmentId
              AND ts.createdAt = (
                 SELECT MAX(ts2.createdAt)
                 FROM TicketStatus ts2
                 WHERE ts2.ticket = t
              )
              AND s.name = :status
            ORDER BY t.createdAt ASC
            """)
    List<Ticket> findByDepartmentIdAndLatestStatusNameOrderByCreatedAtAsc(@Param("departmentId") Long departmentId,
                                                                          @Param("status") String status);
    @Query("""
            SELECT t FROM Ticket t
            JOIN t.ticketCategory c
            JOIN c.departments d
            JOIN TicketStatus ts ON ts.ticket = t
            JOIN Status s ON ts.status = s
            WHERE d.id = :departmentId
              AND ts.createdAt = (
                 SELECT MAX(ts2.createdAt)
                 FROM TicketStatus ts2
                 WHERE ts2.ticket = t
              )
              AND s.name = :status
            ORDER BY t.createdAt DESC
            """)
    List<Ticket> findByDepartmentIdAndLatestStatusNameOrderByCreatedAtDesc(@Param("departmentId") Long departmentId,
                                                                           @Param("status") String status);
}
