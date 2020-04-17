package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Request;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
    ReportService reportService;
    private JavaMailSender javaMailSender;


    @PostMapping("/generate")
    public ResponseEntity generateFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam(name = "semesterId") int semesterId) {
        try {
            reportService.generateExcelFile(multipartFile,semesterId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter")
    public ResponseEntity<Request> getClassByCriteria(@RequestBody QueryParam queryParam) {
        try {
            List<Request> requestList =reportService.findByCriteria(queryParam);
            return new ResponseEntity(requestList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<Request> addReport(@RequestBody Request request,
                                             @RequestHeader("GoogleId") String currentLecturerId) {
        try {
            return new ResponseEntity(reportService.addReport(request,currentLecturerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity<Request> updateReport(@RequestBody Request request) {
        try {
            return new ResponseEntity(reportService.updateReport(request), HttpStatus.OK);
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
