package com.example.anjeonRefactoring.global.init;

import com.example.anjeonRefactoring.report.domain.Tag;
import com.example.anjeonRefactoring.user.domain.User;
import com.example.anjeonRefactoring.user.domain.enumration.RoleType;
import com.example.anjeonRefactoring.report.repository.ReportRepository;
import com.example.anjeonRefactoring.report.repository.TagRepository;
import com.example.anjeonRefactoring.user.repository.UserRepository;
import com.example.anjeonRefactoring.report.service.ReportService;
import jakarta.annotation.PostConstruct;
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
