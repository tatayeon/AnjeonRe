package com.example.anjeonRefactoring.report.controller;

import com.example.anjeonRefactoring.global.annotation.ApiErrorCodeExamples;
import com.example.anjeonRefactoring.report.dto.CreateReportDto;
import com.example.anjeonRefactoring.report.dto.ShowDetailReportDto;
import com.example.anjeonRefactoring.report.dto.ShowMonthlyReportDto;
import com.example.anjeonRefactoring.report.exception.ReportNotFoundException;
import com.example.anjeonRefactoring.report.exception.TagNotFoundException;
import com.example.anjeonRefactoring.report.exception.UserNotFoundException;
import com.example.anjeonRefactoring.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @ApiErrorCodeExamples({
            UserNotFoundException.class,
            TagNotFoundException.class,
    })
    @PostMapping("/create")
    public String createReport(@RequestBody CreateReportDto dto, @AuthenticationPrincipal UserDetails user) {
        String result = reportService.createReport(dto, user.getUsername());
        return result;
    }

    @GetMapping("/show/monthly")
    public ResponseEntity<List<ShowMonthlyReportDto>> showMonthlyReport(){
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        List<ShowMonthlyReportDto> listReport = reportService.showMonthlyReport(year, month);
        return new ResponseEntity<>(listReport, HttpStatus.OK);
    }

    @ApiErrorCodeExamples({
            ReportNotFoundException.class,
    })
    @GetMapping("/changeState/{reportId}")
    public String changeState(@AuthenticationPrincipal UserDetails user, @PathVariable Long reportId) {
        reportService.changeState(reportId);
        return "success";
    }

    @ApiErrorCodeExamples({
            ReportNotFoundException.class,
    })
    @GetMapping("/detail/{reportId}")
    public ResponseEntity<ShowDetailReportDto> detail(@AuthenticationPrincipal UserDetails user, @PathVariable Long reportId) {
        ShowDetailReportDto showDetailReportDto = reportService.detailReport(reportId);
        return ResponseEntity.ok(showDetailReportDto);
    }

}
