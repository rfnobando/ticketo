package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Serializable> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
