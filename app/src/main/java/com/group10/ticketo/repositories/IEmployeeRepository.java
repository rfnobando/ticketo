package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface IEmployeeRepository extends JpaRepository<Employee, Serializable> {
}
