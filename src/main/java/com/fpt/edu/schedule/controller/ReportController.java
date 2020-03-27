package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.Report;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    ReportService reportService;

    @GetMapping("/generate")
    public ResponseEntity generateFile(@RequestParam(name = "fileName") String fileName,@RequestParam(name = "semesterId") int semesterId) {
        try {
            reportService.generateExcelFile(fileName,semesterId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter")
    public ResponseEntity<Report> getClassByCriteria(@RequestBody QueryParam queryParam) {
        try {
            List<Report> reportList =reportService.findByCriteria(queryParam);
            return new ResponseEntity(reportList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<Report> addReport(@RequestBody Report report) {
        try {
            return new ResponseEntity(reportService.addReport(report), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity<Report> updateReport(@RequestBody Report report) {
        try {
            return new ResponseEntity(reportService.updateReport(report), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{reportId}/updateStatus")
    public ResponseEntity<Report> updateStatus(@RequestParam Status status, @PathVariable("reportId") int reportId) {
        try {
            return new ResponseEntity(reportService.updateStatus(status,reportId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{reportId}")
    public ResponseEntity remove(@PathVariable("reportId") int expectedId) {
        try {
            reportService.removeReportById(expectedId);
            return new ResponseEntity( HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
