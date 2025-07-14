package com.example.anjeonRefactoring.user.repository;

import com.example.anjeonRefactoring.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
