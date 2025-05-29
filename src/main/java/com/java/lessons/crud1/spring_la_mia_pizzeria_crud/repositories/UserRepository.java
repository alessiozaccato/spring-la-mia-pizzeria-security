package com.java.lessons.crud1.spring_la_mia_pizzeria_crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.model.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
