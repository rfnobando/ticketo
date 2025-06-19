package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.dtos.EmployeeRegistrationDTO;

public interface IEmployeeService {
    void registerEmployee(EmployeeRegistrationDTO employeeRegistrationDTO);
}
