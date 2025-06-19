package com.group10.ticketo.services;

import com.group10.ticketo.entities.Department;

import java.util.List;

public interface IDepartmentService {
    Department getDepartmentById(Long id);
    List<Department> getAllDepartments();
}
