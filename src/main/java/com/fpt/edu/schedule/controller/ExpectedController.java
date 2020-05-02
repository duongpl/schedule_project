package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.ExpectedService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/expects")
public class ExpectedController  {
    ExpectedService expectedService;
    @PostMapping
    public ResponseEntity addExpected(@RequestBody Expected expected) {
            return new ResponseEntity(expectedService.addExpected(expected),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateExpected(@RequestBody Expected expected) {
            return new ResponseEntity(expectedService.updateExpected(expected),HttpStatus.OK);
    }
    @PostMapping("/filter")
    public ResponseEntity getExpectedByCriteria(@RequestBody QueryParam queryParam) {
            List<Expected> expectedList =expectedService.findByCriteria(queryParam);
            return new ResponseEntity(expectedList, HttpStatus.OK);

    }
    @GetMapping("/reuse")
    public ResponseEntity reuseExpected(@RequestParam(name = "semesterId") int semesterId
            , @RequestParam(name = "lecturerGoogleId") String lecturerGoogleId) {

            return new ResponseEntity(expectedService.saveExistedExpected(lecturerGoogleId,semesterId),HttpStatus.OK);

    }
    @DeleteMapping("/{expectedId}")
    public ResponseEntity remove(@PathVariable("expectedId") int expectedId) {

            expectedService.removeExpectedById(expectedId);
            return new ResponseEntity( HttpStatus.OK);

    }
    @GetMapping()
    public ResponseEntity getByLecturerAndSemester(@RequestParam(name = "lecturerId") String lecturerId,
                                                   @RequestParam(name = "semesterId") int semesterId) {
            return new ResponseEntity( expectedService.getExpectedByLecturerAndSemester(lecturerId,semesterId),HttpStatus.OK);

    }
}
