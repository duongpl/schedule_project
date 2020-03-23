package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.repository.impl.QueryParam;
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
        try {
            return new ResponseEntity(expectedService.addExpected(expected),HttpStatus.OK);
        } catch (InvalidRequestException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity updateExpected(@RequestBody Expected expected) {
        try {
            return new ResponseEntity(expectedService.updateExpected(expected),HttpStatus.OK);
        } catch (InvalidRequestException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter")
    public ResponseEntity getExpectedByCriteria(@RequestBody QueryParam queryParam) {
        try {

            List<Expected> expectedList =expectedService.findByCriteria(queryParam);
            return new ResponseEntity(expectedList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{expectedId}")
    public ResponseEntity remove(@PathVariable("expectedId") int expectedId) {
        try {
            return new ResponseEntity(expectedService.removeExpectedById(expectedId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
