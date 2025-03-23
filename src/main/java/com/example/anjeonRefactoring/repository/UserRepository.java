package com.example.anjeonRefactoring.repository;

import com.example.anjeonRefactoring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

}
