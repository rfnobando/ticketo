package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
