package com.group10.ticketo.services;

import com.group10.ticketo.dtos.CustomerRegistrationDTO;

public interface ICustomerService {
    /**
     * Registers a new customer with the provided details.
     *
     * @param customerRegistrationDTO the DTO containing customer registration details
     * @return true if registration is successful, false otherwise
     */
    void registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);

    /**
     * Checks if a customer with the given email already exists.
     *
     * @param email the email to check
     * @return true if a customer with the email exists, false otherwise
     */
    boolean doesCustomerExist(String email);
}
