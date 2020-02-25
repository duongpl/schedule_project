package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.service.base.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    ScheduleService scheduleService;
    @GetMapping
    public ResponseEntity getAllSchedule() {
        try {
            scheduleService.getAllSchedule();
            return new ResponseEntity(scheduleService.getAllSchedule(),HttpStatus.OK);
        } catch (InvalidRequestException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
