package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.service.base.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    ReportService reportService;

    @GetMapping("/generate")
    public ResponseEntity generateFile(@RequestParam(name = "fileName") String fileName) {
        try {
            reportService.generateExcelFile(fileName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
