package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/timetables")
public class TimetableController {
    TimetableService timetableService;

    @GetMapping("/filter")
    public ResponseEntity getTimetableBySemester(@RequestParam("semesterId") int semesterId) {
        try {

           List<TimetableEdit> timetableDetails = timetableService.getTimetableBySemester(semesterId);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
