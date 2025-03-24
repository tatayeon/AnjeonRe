package com.example.anjeonRefactoring.repository;

import com.example.anjeonRefactoring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
