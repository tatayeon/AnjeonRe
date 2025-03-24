package com.example.anjeonRefactoring.domain;

import com.example.anjeonRefactoring.domain.enumration.ReportState;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@NoArgsConstructor
@Entity
@Getter
public class Report {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long createZoneId;

    private String content;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReportState reportState;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "user_id") // 외래키 설정
    private User user;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<ReportTagMap> tags = new ArrayList<>();

    @Builder
    public Report(Long createZoneId,String content, LocalDateTime createdAt, ReportState reportState, User user) {
        this.createZoneId = createZoneId;
        this.content = content;
        this.createdAt = createdAt;
        this.reportState = reportState;
        this.user = user;
    }
}
