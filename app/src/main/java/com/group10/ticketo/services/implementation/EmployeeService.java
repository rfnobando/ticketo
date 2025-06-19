package com.group10.ticketo.services.implementation;

import com.group10.ticketo.dtos.EmployeeRegistrationDTO;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Salary;
import com.group10.ticketo.repositories.IEmployeeRepository;
import com.group10.ticketo.services.IDepartmentService;
import com.group10.ticketo.services.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IDepartmentService departmentService;

    public EmployeeService(IEmployeeRepository employeeRepository, IDepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    @Override
    public void registerEmployee(EmployeeRegistrationDTO employeeRegistrationDTO) {
        String fileNumber;
        // Generar legajo único con UUID acortado
        do {
            fileNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (employeeRepository.existsByFileNumber(fileNumber));
        Salary salary=new Salary(employeeRegistrationDTO.getAmount())
        Employee employee=new Employee(
                fileNumber,
                departmentService.getDepartmentById(employeeRegistrationDTO.getDeparmentId(),
                        List<Salary>salarys=new Salary()));
    }
}
