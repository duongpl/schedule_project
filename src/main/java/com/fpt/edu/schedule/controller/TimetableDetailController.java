package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.TimeTableDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/schedules")
public class TimetableDetailController {
    TimeTableDetailService timeTableDetailService;
    @GetMapping
    public ResponseEntity getAllSchedule() {
        try {
            timeTableDetailService.getAllSchedule();
            return new ResponseEntity(timeTableDetailService.getAllSchedule(),HttpStatus.OK);
        } catch (InvalidRequestException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter")
    public ResponseEntity<ClassName> getScheduleByCriteria(@RequestBody QueryParam queryParam) {
        try {

            List<TimetableDetail> timetableDetails = timeTableDetailService.findByCriteria(queryParam);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping
//    public ResponseEntity getScheduleByUserId(@RequestParam(value = "userId") String userId) {
//        try {
//            Schedule schedule = scheduleService.getScheduleByUserId(userId);
//            return new ResponseEntity(schedule,HttpStatus.OK);
//        } catch (InvalidRequestException e) {
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//        catch (Exception e) {
//            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
