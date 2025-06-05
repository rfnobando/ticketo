package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private Set<Ticket> tickets;

    @ManyToMany(mappedBy = "ticket_categories")
    private Set<Department> departments;

}