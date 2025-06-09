package com.group10.ticketo.repositories;

import com.group10.ticketo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
}
