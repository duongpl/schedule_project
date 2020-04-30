package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Confirmation;
import com.fpt.edu.schedule.service.base.ConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/confirmations")
public class ConfirmationController {
    ConfirmationService confirmationService;
    @PostMapping
    public ResponseEntity addConfirm(@RequestParam("semesterId") int semesterId,
                                     @RequestBody List<Integer> lecturerIds) {
        try {
            return new ResponseEntity(confirmationService.save(lecturerIds,semesterId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping()
    public ResponseEntity updateStatusConfirm(@RequestBody Confirmation confirmation) {
        try {
            return new ResponseEntity(confirmationService.update(confirmation), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping()
    public ResponseEntity getByLecturerAndSemester(@RequestParam("semesterId") int semesterId,
                                                   @RequestHeader("GoogleId") String lecturerId) {
        try {
            return new ResponseEntity(confirmationService.getByLecturerAndSemester(lecturerId,semesterId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
