package com.example.anjeonRefactoring.init;

import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.domain.User;
import com.example.anjeonRefactoring.domain.enumration.RoleType;
import com.example.anjeonRefactoring.repository.ReportRepository;
import com.example.anjeonRefactoring.repository.TagRepository;
import com.example.anjeonRefactoring.repository.UserRepository;
import com.example.anjeonRefactoring.service.ReportService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class initData {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReportService reportService;
    private final PasswordEncoder passwordEncoder;
    private final TagRepository tagRepository;

    @PostConstruct
    @Transactional
    public void init(){
        User admin = new User("admin", "admin@example.com", passwordEncoder.encode("admin123"), RoleType.WORKER);
        User worker = new User("worker", "worker@example.com", passwordEncoder.encode("worker123"), RoleType.WORKER);
        User user = new User("user", "user@example.com", passwordEncoder.encode("user123"), RoleType.WORKER);

        userRepository.save(admin);
        userRepository.save(worker);
        userRepository.save(user);

        Tag tag1 = new Tag("기계","기계결함");
        Tag tag2 = new Tag("기계", "소음이상");
        Tag tag3 = new Tag("전체", "이상함");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

    }






}
