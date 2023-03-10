package com.example.diploma.repository;

import com.example.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {
    Optional<User> findUserByLogin(String login);

}
