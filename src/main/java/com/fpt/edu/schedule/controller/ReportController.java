package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.service.base.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ReportController {
    ReportService reportService;

    @GetMapping("/generateFile")
    public String generateFile(@RequestParam(name = "fileName") String fileName) {
        reportService.generateExcelFile(fileName);
        return "done";
    }
}
