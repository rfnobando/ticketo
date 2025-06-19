package com.group10.ticketo.services.implementation;

import com.group10.ticketo.constants.RoleConstants;
import com.group10.ticketo.dtos.EmployeeRegistrationDTO;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Salary;
import com.group10.ticketo.entities.User;
import com.group10.ticketo.exceptions.RoleNotFoundException;
import com.group10.ticketo.repositories.*;
import com.group10.ticketo.services.IDepartmentService;
import com.group10.ticketo.services.IEmployeeService;
import com.group10.ticketo.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IDepartmentService departmentService;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final ISalaryRepository salaryRepository;

    public EmployeeService(IEmployeeRepository employeeRepository, IDepartmentService departmentService, IRoleRepository roleRepository, IUserRepository userRepository, IDepartmentRepository departmentRepository, ISalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.salaryRepository = salaryRepository;
    }

    @Override
    public void registerEmployee(EmployeeRegistrationDTO employeeRegistrationDTO) {
        String fileNumber;
        // Generar legajo único con UUID acortado
        do {
            fileNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (employeeRepository.existsByFileNumber(fileNumber));

        User user = User.builder()
                .email(employeeRegistrationDTO.getEmail())
                .password(SecurityUtils.encryptPassword(employeeRegistrationDTO.getPassword()))
                .roles(List.of(
                        roleRepository.findByRole(RoleConstants.EMPLOYEE).orElseThrow(() -> new RoleNotFoundException("Employee role not found")))
                )
                .build();

        userRepository.save(user);

        Employee employee = new Employee();
        employee.setName(employeeRegistrationDTO.getName());
        employee.setSurname(employeeRegistrationDTO.getSurname());
        employee.setFileNumber(fileNumber);
        employee.setDepartment(departmentRepository.findById(employeeRegistrationDTO.getDeparmentId()).orElseThrow(() -> new RuntimeException("Development department not found")));
        employee.setUser(user);

        Salary salary= new Salary();
        salary.setAmount(employeeRegistrationDTO.getAmount());
        salary.setEmployee(employee);
        employeeRepository.save(employee);
        salaryRepository.save(salary);
    }
}
