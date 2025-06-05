package com.group10.ticketo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Person {

    @Column(name = "file_number")
    private String fileNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",nullable = false)
    private Department department;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<Pay> payments;

}