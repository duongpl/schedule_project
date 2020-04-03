package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.service.base.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {
    SubjectService subjectService;
    @PostMapping("/filter")
    public ResponseEntity<Subject> getSubjectByCriteria(@RequestParam("semesterId") int semesterId,
                                                        @RequestHeader("GoogleId")String hodGoogleId) {
        try {
            List<Subject> subjectList =subjectService.getAllSubjectBySemester(semesterId,hodGoogleId);

            return new ResponseEntity(subjectList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
