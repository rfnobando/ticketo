package com.group10.ticketo.services.implementation;

import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.entities.Customer;
import com.group10.ticketo.entities.Role;
import com.group10.ticketo.entities.User;
import com.group10.ticketo.exceptions.RoleNotFoundException;
import com.group10.ticketo.exceptions.UserValidationException;
import com.group10.ticketo.repositories.ICustomerRepository;
import com.group10.ticketo.repositories.IRoleRepository;
import com.group10.ticketo.repositories.IUserRepository;
import com.group10.ticketo.services.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ICustomerRepository customerRepository;

    public CustomerService(IUserRepository userRepository, IRoleRepository roleRepository, ICustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public void registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        // Check if the email already exists
        if (doesCustomerExist(customerRegistrationDTO.getEmail()))
            throw new UserValidationException("Email already exists");
        if(customerRegistrationDTO.getPassword()!= customerRegistrationDTO.getConfirmPassword())
            throw new UserValidationException("Passwords do not match");
        // Create a new user
        Role defaultRole = roleRepository.findByRole("ROLE_CUSTOMER")
                .orElseThrow(() -> new RoleNotFoundException("Rol CUSTOMER no encontrado"));
        User user = User.builder()
                .email(customerRegistrationDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(customerRegistrationDTO.getPassword()))
                .roles(List.of(defaultRole))
                .build();
        user = userRepository.save(user);

        // Create a new customer
        Customer customer = new Customer();
        customer.setName(customerRegistrationDTO.getName());
        customer.setSurname(customerRegistrationDTO.getSurname());
        customer.setPhoneNumber(customerRegistrationDTO.getPhoneNumber());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    @Override
    public boolean doesCustomerExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
