package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person {

    @Column(name = "phone_number")
    private String phoneNumber;

}