package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.Department;
import com.group10.ticketo.repositories.IDepartmentRepository;
import com.group10.ticketo.services.IDepartmentService;

public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;

    public DepartmentService(IDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department getDepartmentById(Long id) {
        Department department=departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el departamento"));
        return department;
    }
}
