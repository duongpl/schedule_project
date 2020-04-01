package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Report;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    ReportService reportService;
    private JavaMailSender javaMailSender;


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
    public ResponseEntity<Report> addReport(@RequestBody Report report,
                                            @RequestHeader("GoogleId") String currentLecturerId) {
        try {
            return new ResponseEntity(reportService.addReport(report,currentLecturerId), HttpStatus.OK);
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
    @DeleteMapping("/{reportId}")
    public ResponseEntity remove(@PathVariable("reportId") int expectedId) {
        try {
            reportService.removeReportById(expectedId);
            return new ResponseEntity( HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendMail")
    public String sendMail(@RequestParam("lecturerId") String lecturerId) {
        SimpleMailMessage msg = new SimpleMailMessage();
        try {

            msg.setSubject("Logout");
            msg.setText("Ban vua logout");
            javaMailSender.send(msg);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {

        }
        return "redirect:/login";
    }

}
