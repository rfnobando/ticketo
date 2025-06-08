package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface IDepartmentRepository extends JpaRepository<Department, Serializable> {
    Optional<Department> findByName(String name);
}
