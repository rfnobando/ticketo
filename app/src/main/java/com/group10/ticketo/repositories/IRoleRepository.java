package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Serializable> {
    Optional<Role> findByRole(String role);

}