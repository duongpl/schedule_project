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
        List<TimetableDetail> timetableDetails = timeTableDetailService.findByCriteria(queryParam,1);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);

    }

    @PutMapping()
    public ResponseEntity updateTimeTableDetail(@RequestBody TimetableDetailDTO request) {
            return new ResponseEntity(timeTableDetailService.updateTimetableDetail(request), HttpStatus.OK);

    }
    @PostMapping("/filter/forEdit")
    public ResponseEntity getTimetableForEdit(@RequestBody QueryParam queryParam,
                                              @RequestParam("groupBy") String groupBy,
                                              @RequestParam("semesterId") int semesterId) {
            List<TimetableEdit> timetableDetails = timeTableDetailService.getTimetableForEdit(queryParam,groupBy,semesterId);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);

    }
    @PostMapping("/filter/forView")
    public ResponseEntity getTimetableForView(@RequestBody QueryParam queryParam) {
            List<TimetableView> timetableDetails = timeTableDetailService.getTimetableForView(queryParam);
            return new ResponseEntity(timetableDetails, HttpStatus.OK);

    }
    @PutMapping("swap")
    public ResponseEntity swapLecturerTimetable(@RequestBody List<Integer> ids,
                                                @RequestParam String type) {
          timeTableDetailService.swapTwoTimetableDetail(ids,type);
            return new ResponseEntity(HttpStatus.OK);
    }

}
