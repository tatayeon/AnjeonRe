package com.example.anjeonRefactoring.report.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class ReportTagMap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_tag_map_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public ReportTagMap(Report report, Tag tag) {
        this.report = report;
        this.tag = tag;
    }

}
