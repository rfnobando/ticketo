package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ticket_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCategory  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "ticketCategory",fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @ManyToMany(mappedBy = "ticketCategories")
    private List<Department> departments;

}