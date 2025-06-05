package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "department_ticket_category", // Nombre de la tabla de unión
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_category_id")
    )
    private Set<TicketCategory> ticketCategories;
}