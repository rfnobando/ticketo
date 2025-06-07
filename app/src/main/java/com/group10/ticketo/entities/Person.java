package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    protected String name;

    protected String surname;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected User user;


    @OneToMany(mappedBy = "person",fetch = FetchType.LAZY)
    protected List<TicketMessage> ticketMessages;
}