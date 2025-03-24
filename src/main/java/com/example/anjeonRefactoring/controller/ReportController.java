package com.example.anjeonRefactoring.controller;

import com.example.anjeonRefactoring.domain.Report;
import com.example.anjeonRefactoring.domain.User;
import com.example.anjeonRefactoring.repuestDto.CreateReportDto;
import com.example.anjeonRefactoring.responsDto.ShowMonthlyReportDto;
import com.example.anjeonRefactoring.service.ReportService;
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

}
