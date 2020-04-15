package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/timetables")
public class TimetableController {
    TimetableService timetableService;
    @PostMapping("/auto-arrange")
    public ResponseEntity<Subject> autoArrange(@RequestParam("semesterId") int semesterId,
                                                        @RequestHeader("GoogleId")String hodGoogleId) {
        try {
            timetableService.autoArrange(semesterId,hodGoogleId);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
