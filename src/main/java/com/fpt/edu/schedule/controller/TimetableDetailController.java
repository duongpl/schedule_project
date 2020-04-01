package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.dto.TimetableView;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableEdit;
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
@RequestMapping("/api/v1/timetableDetails")
public class TimetableDetailController {
    TimeTableDetailService timeTableDetailService;

    @PostMapping("/filter")
    public ResponseEntity<ClassName> getScheduleByCriteria(@RequestBody QueryParam queryParam) {
        try {
            List<TimetableDetail> timetableDetails = timeTableDetailService.findByCriteria(queryParam);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity updateTimeTableDetail(@RequestBody TimetableDetailDTO request) {
        try {
            return new ResponseEntity(timeTableDetailService.updateTimetableDetail(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter/forEdit")
    public ResponseEntity getTimetableForEdit(@RequestBody QueryParam queryParam) {
        try {

            List<TimetableEdit> timetableDetails = timeTableDetailService.getTimetableForEdit(queryParam);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter/forView")
    public ResponseEntity getTimetableForView(@RequestBody QueryParam queryParam) {
        try {

            List<TimetableView> timetableDetails = timeTableDetailService.getTimetableForView(queryParam);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
