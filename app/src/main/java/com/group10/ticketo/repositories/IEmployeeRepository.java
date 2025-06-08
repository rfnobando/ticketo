package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEmployeeRepository extends JpaRepository<Employee, Long> {


}
