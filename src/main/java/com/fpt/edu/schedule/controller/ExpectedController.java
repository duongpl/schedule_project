package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.ExpectedDTO;
import com.fpt.edu.schedule.service.base.ExpectedService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/expects")
public class ExpectedController  {
    ExpectedService expectedService;
    @PostMapping
    public ResponseEntity addExpected(@RequestBody ExpectedDTO expectedDTO) {
        try {
            expectedService.addExpected(expectedDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidRequestException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getExpectedByUserId(@RequestParam(value = "userId", defaultValue = "") String userId) {
        try {
            return new ResponseEntity(expectedService.getExpectedByUserId(userId),HttpStatus.OK);
        } catch (InvalidRequestException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
