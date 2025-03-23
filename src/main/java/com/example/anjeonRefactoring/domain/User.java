package com.example.anjeonRefactoring.domain;

import com.example.anjeonRefactoring.domain.enumration.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.WORKER; // 기본값을 USER로 설정

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 일대다 관계 설정
    private List<Report> reports = new ArrayList<>();

    public User(String username, String email, String password, RoleType role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
