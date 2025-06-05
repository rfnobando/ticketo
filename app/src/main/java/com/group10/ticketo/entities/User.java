package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @CreationTimestamp
    @Column(name = "registration_date")
    private String registrationDate;

    @OneToOne(mappedBy = "user")
    private Person person;

    @ManyToMany
    @JoinTable(
            name = "user_role", // Nombre de la tabla de unión
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}