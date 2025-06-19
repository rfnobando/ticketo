package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface ISalaryRepository extends JpaRepository<Salary, Serializable> {
}
